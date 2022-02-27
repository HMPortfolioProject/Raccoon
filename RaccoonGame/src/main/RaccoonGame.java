package main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

import constants.*;
import logic.ReSizingImageLogic;
import thread.GameThread;

/**
 * 너구리 게임 프레임 클래스
 * @author CheonMungi
 *
 */
@SuppressWarnings("serial")
public class RaccoonGame extends JFrame
{
	/** 넓이 */
	protected static int _FRAME_SIZE_WIDTH = 1600;
	/** 높이 */
	protected static int _FRAME_SIZE_HEIGHT = 900;
	/** 그래픽 환경*/
	private GraphicsEnvironment _ge = null;
	/** 그래픽 디바이스*/
	private GraphicsDevice _defaultSd = null;
	/** 버퍼 이미지*/
	private Image _bufferImage = null;
	/** 스크린 그래픽*/
	private Graphics _screenGraphic = null;
	/** 이미지 : 시작 화면*/
	private Image _startImg = new ImageIcon( ImageConstants.BACKGROUND_START ).getImage();
	/** 이미지 : 스테이지*/
	private Image _curStageImg = new ImageIcon( ImageConstants.STAGE_FIRST ).getImage();
	/** 배경 사이즈*/
	private int _bgWidth = 0;
	private int _bgHeight = 0;
	/** 임시파일 이미지 리스트*/
	private HashMap<String, File> _tempImgMap = null;
	/** 게임 전반적인 진행을 지원하는 스레드*/
	private GameThread _gameThread = new GameThread();
	/** 시작 화면인지*/
	private boolean _isStartScreen = true;
	/** 게임 화면인지*/
	private boolean _isGameScreen = false;

	/**
	 * 컨스트럭터
	 */
	public RaccoonGame()
	{
		_init();
	}

	/**
	 * 화면 초기화
	 */
	private void _init()
	{
		setTitle( LabelConstants.GAME_TITLE );
		setSize( _FRAME_SIZE_WIDTH, _FRAME_SIZE_HEIGHT );
		setResizable( false );
		setLocationRelativeTo( null );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		_setFrameLocation();

		// 초기 사이즈 저장
		_bgWidth = _startImg.getWidth( null );
		_bgHeight = _startImg.getHeight( null );

		if( _tempImgMap == null )
		{
			_tempImgMap = new HashMap<String, File>();
		}

		// Alt + Enter키로 풀스크린, 창모드 전환하는 키 리스너 추가
		addKeyListener( new GameKeyListener() );
		// 닫기버튼 클릭시 이벤트
		addWindowListener( new WindowAdapter()
		{
			@Override
			public void windowClosing( WindowEvent e )
			{
				System.exit( 0 );
			};

		} );

		setVisible( true );
		setLayout( null );
	}

	/**
	 * 화면 위치 설정
	 */
	private void _setFrameLocation()
	{
		_ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		_defaultSd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		GraphicsDevice[] gds = _ge.getScreenDevices();

		// 모니터 가운데로 화면 위치 설정
		if( gds.length > 0 )
		{
			int width = gds[ 0 ].getDefaultConfiguration().getBounds().width;
			int height = gds[ 0 ].getDefaultConfiguration().getBounds().height;

			setLocation( ( ( width / 2 ) - ( getSize().width / 2 ) ) + gds[ 0 ].getDefaultConfiguration().getBounds().x,
				( ( height / 2 ) - ( getSize().height / 2 ) ) + gds[ 0 ].getDefaultConfiguration().getBounds().y );
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			setVisible( true );
		}
		else
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow( this );
		}
	}

	@Override
	public void paint( Graphics g )
	{
		_bufferImage = createImage( getSize().width, getSize().height );
		_screenGraphic = _bufferImage.getGraphics();
		_screenDraw( _screenGraphic );
		g.drawImage( _bufferImage, 0, 0, null );
	}

	/**
	 * 해상도에 맞춰 이미지 그리기
	 * @param g
	 */
	private void _screenDraw( Graphics g )
	{
		String path = null;
		if( _isStartScreen )
		{
			if( _bgWidth != getSize().width && _bgHeight != getSize().height )
			{
				// 배경 사이즈와 현재 사이즈가 일치 하지 않을경우 해상도가 변경된 것으로 간주
				// 풀스크린, 창모드 전환시 이미지 크기 조정
				path = ImageConstants.BACKGROUND_START;
				_startImg = ReSizingImageLogic.reSizingImg( this, _tempImgMap, path );

				_bgWidth = getSize().width;
				_bgHeight = getSize().height;
			}
			else
			{
				g.drawImage( _startImg, 0, 0, null );
			}
		}
		if( _isGameScreen )
		{
			if( _bgWidth != getSize().width && _bgHeight != getSize().height )
			{
				// 스테이지 사이즈와 현재 사이즈가 일치 하지 않을경우 해상도가 변경된 것으로 간주
				// 풀스크린, 창모드 전환시 이미지 크기 조정
				path = ImageConstants.STAGE_FIRST;
				_curStageImg = ReSizingImageLogic.reSizingImg( this, _tempImgMap, path );

				_bgWidth = getSize().width;
				_bgHeight = getSize().height;
			}
			else
			{
				g.drawImage( _curStageImg, 0, 0, null );
				_gameThread.gameDraw( g );

			}
		}
		repaint();
	}

	/**
	 * 스크린모드를 변경
	 * @param isFullScreen 현재 풀스크린인지
	 */
	protected void changeScreenMode( boolean isFullScreen )
	{
		if( isFullScreen )
		{
			dispose();
			_defaultSd.setFullScreenWindow( null );
			setVisible( true );
		}
		else
		{
			dispose();
			_defaultSd.setFullScreenWindow( this );
			setVisible( true );
		}
	}

	/**
	 * 게임 시작
	 */
	private void _gameStart()
	{
		Timer loadingTimer = new Timer();
		TimerTask loadingTask = new TimerTask()
		{
			@Override
			public void run()
			{
				_isStartScreen = false;
				_isGameScreen = true;
				_gameThread.start();
			}
		};
		loadingTimer.schedule( loadingTask, 2000 );
	}

	/**
	 * 풀스크린, 창모드 전환 키리스너
	 * @author CheonMungi
	 *
	 */
	private class GameKeyListener implements KeyListener
	{
		/** 입력된 키코드 해시셋*/
		private HashSet<Integer> _keyCodeSet = new HashSet<Integer>();
		/** 현재 풀스크린인지*/
		private boolean _isFullScreen = false;

		@Override
		public void keyPressed( KeyEvent e )
		{
			if( e.getKeyCode() == KeyEvent.VK_ALT || e.getKeyCode() == KeyEvent.VK_ENTER )
			{
				// Alt키 또는 Enter키가 눌러졌다면 셋에 추가
				_keyCodeSet.add( Integer.valueOf( e.getKeyCode() ) );
			}
			else
			{
				switch( e.getKeyCode() )
				{
					// TODO : UP,DOWN,LEFT,RIGHT 캐릭터 이동 구현 필요
					case KeyEvent.VK_KP_UP:
						break;
					case KeyEvent.VK_KP_DOWN:
						break;
					case KeyEvent.VK_KP_LEFT:
						break;
					case KeyEvent.VK_KP_RIGHT:
						break;
					case KeyEvent.VK_SPACE:
						if( _isStartScreen )
						{
							// TODO : 게임 시작
							_gameStart();
						}
						else
						{
							// TODO : 캐릭터 점프 (짧은 점프, 길제 점프 구현 필요)
						}
						break;
					case KeyEvent.VK_ESCAPE:
						System.exit( 0 );
						break;
				}
			}
		}

		@Override
		public void keyReleased( KeyEvent e )
		{
			if( !_keyCodeSet.isEmpty() && _keyCodeSet.size() == 2 )
			{
				if( !_isFullScreen )
				{
					// 풀스크린으로 변경
					changeScreenMode( _isFullScreen );
					_isFullScreen = true;
				}
				else
				{
					// 창모드로 변경
					changeScreenMode( _isFullScreen );
					_isFullScreen = false;
				}
				_keyCodeSet.clear();
				return;
			}
			else
			{
				_keyCodeSet.clear();
			}

			switch( e.getKeyCode() )
			{
				case KeyEvent.VK_KP_UP:
					break;
				case KeyEvent.VK_KP_DOWN:
					break;
				case KeyEvent.VK_KP_LEFT:
					break;
				case KeyEvent.VK_KP_RIGHT:
					break;
				case KeyEvent.VK_SPACE:
					if( _isGameScreen )
					{
						// TODO : 캐릭터 점프 (짧은 점프, 길제 점프 구현 필요)
					}
					break;
			}
		}

		@Override
		public void keyTyped( KeyEvent e )
		{
			// TODO Auto-generated method stub
		}
	}
}

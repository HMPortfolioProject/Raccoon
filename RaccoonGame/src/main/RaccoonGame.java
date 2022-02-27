package main;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

import constants.*;
import thread.GameThread;

/**
 * 너구리 게임 프레임 클래스
 * @author CheonMungi
 *
 */
@SuppressWarnings("serial")
public class RaccoonGame extends JFrame
{
	/** HD 가로 사이즈 */
	public static int FRAME_HD_SIZE_WIDTH = 1600;
	/** HD 세로 사이즈 */
	public static int FRAME_HD_SIZE_HEIGHT = 900;
	/** FHD 가로 사이즈*/
	public static int FRAME_FHD_SIZE_WIDTH = 1920;
	/** FHD 세로 사이즈*/
	public static int FRAME_FHD_SIZE_HEIGHT = 1080;
	/** UHD 가로 사이즈*/
	public static int FRAME_UHD_SIZE_WIDTH = 3840;
	/** 그래픽 환경*/
	private GraphicsEnvironment _ge = null;
	/** 1번 디스플레이*/
	private GraphicsDevice _gameDisplay = null;
	/** 2번 디스플레이*/
	private GraphicsDevice _subDisplay = null;
	/** 디스플레이 가로 해상도 */
	private int _dsplayLocationX = 0;
	/** 버퍼 이미지*/
	private Image _bufferImage = null;
	/** 스크린 그래픽*/
	private Graphics _screenGraphic = null;
	/** 이미지 : 시작 화면*/
	private Image _startImg = null;
	/** 이미지 : 스테이지*/
	private Image _curStageImg = null;
	/** 배경 사이즈*/
	private int _bgWidth = 0;
	private int _bgHeight = 0;
	/** 게임 전반적인 진행을 지원하는 스레드*/
	private GameThread _gameThread = new GameThread();
	/** 시작 화면인지*/
	private boolean _isStartScreen = true;
	/** 게임 화면인지*/
	private boolean _isGameScreen = false;
	/** 현재 풀스크린인지*/
	private boolean _isFullScreen = false;

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
		setResizable( false );
		setLocationRelativeTo( null );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setIconImage( new ImageIcon( ImageConstants.DIR_DPI_HD + ImageConstants.RACCOON_LEFT_JUMP1 ).getImage() );

		_dsplayLocationX = Toolkit.getDefaultToolkit().getScreenSize().width;
		_ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		_gameDisplay = _ge.getDefaultScreenDevice();

		if( _ge.getScreenDevices().length == 2 )
		{
			_subDisplay = _ge.getScreenDevices()[ 1 ];
		}

		_setSize();

		_setFrameLocation();

		// 초기 사이즈 저장
		_bgWidth = _startImg.getWidth( null );
		_bgHeight = _startImg.getHeight( null );

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
		addComponentListener( new WindowMoveListener() );
		setVisible( true );
		setLayout( null );
	}

	/**
	 * 화면 사이즈를 설정합니다.
	 */
	private void _setSize()
	{
		if( _gameDisplay.getDisplayMode().getWidth() == FRAME_FHD_SIZE_WIDTH )
		{
			setSize( FRAME_HD_SIZE_WIDTH, FRAME_HD_SIZE_HEIGHT );

			_startImg = new ImageIcon( ImageConstants.DIR_DPI_HD + ImageConstants.BACKGROUND_START ).getImage();
			_curStageImg = new ImageIcon( ImageConstants.DIR_DPI_HD + ImageConstants.STAGE_FIRST ).getImage();
		}
		if( _gameDisplay.getDisplayMode().getWidth() == FRAME_UHD_SIZE_WIDTH )
		{
			setSize( FRAME_FHD_SIZE_WIDTH, FRAME_FHD_SIZE_HEIGHT );
			_startImg = new ImageIcon( ImageConstants.DIR_DPI_FHD + ImageConstants.BACKGROUND_START ).getImage();
			_curStageImg = new ImageIcon( ImageConstants.DIR_DPI_FHD + ImageConstants.STAGE_FIRST ).getImage();
		}
	}

	/**
	 * 화면 위치 설정
	 */
	private void _setFrameLocation()
	{
		GraphicsDevice[] gds = _ge.getScreenDevices();

		// 모니터 가운데로 화면 위치 설정
		if( gds.length > 0 )
		{
			int width = gds[ 0 ].getDefaultConfiguration().getBounds().width;
			int height = gds[ 0 ].getDefaultConfiguration().getBounds().height;

			setLocation( ( ( width / 2 ) - ( getSize().width / 2 ) ) + gds[ 0 ].getDefaultConfiguration().getBounds().x,
				( ( height / 2 ) - ( getSize().height / 2 ) ) + gds[ 0 ].getDefaultConfiguration().getBounds().y );
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
		try
		{
			if( _isStartScreen )
			{
				//				if( _bgWidth != getSize().width && _bgHeight != getSize().height )
				// 풀스크린, 창모드 전환시 이미지 크기 조정
				_startImg = _changeImg( ImageConstants.BACKGROUND_START );
				g.drawImage( _startImg, 0, 0, null );
			}
			if( _isGameScreen )
			{
				// 풀스크린, 창모드 전환시 이미지 크기 조정
				_curStageImg = _changeImg( ImageConstants.STAGE_FIFTH );
				g.drawImage( _curStageImg, 0, 0, null );
				_gameThread.gameDraw( g, _isFullScreen );

			}
			repaint();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * 이미지를 변경합니다
	 * @param imgPath 이미지 경로
	 * @return 이미지
	 */
	private Image _changeImg( String imgPath )
	{
		Image img = null;
		if( _isFullScreen )
		{
			if( _gameDisplay.getDisplayMode().getWidth() == FRAME_FHD_SIZE_WIDTH )
			{
				// 해상도가 FHD일 경우 이미지를 FHD사이즈로 변경
				img = new ImageIcon( ImageConstants.DIR_DPI_FHD + imgPath ).getImage();
			}
		}
		else
		{
			// 창모드
			if( _gameDisplay.getDisplayMode().getWidth() == FRAME_FHD_SIZE_WIDTH )
			{
				// 해상도가 FHD일 경우 이미지를 HD사이즈로 변경
				img = new ImageIcon( ImageConstants.DIR_DPI_HD + imgPath ).getImage();
			}
		}

		//		if( _isFullScreen )
		//		{
		//			if( _gameDisplay.getDisplayMode().getWidth() == FRAME_FHD_SIZE_WIDTH )
		//			{
		//				img = new ImageIcon( ImageConstants.DIR_DPI_FHD + imgPath ).getImage();
		//			}
		//			if( _gameDisplay.getDisplayMode().getWidth() == FRAME_UHD_SIZE_WIDTH )
		//			{
		//				img = new ImageIcon( ImageConstants.DIR_DPI_UHD + imgPath ).getImage();
		//			}
		//		}
		//		else
		//		{
		//			if( _gameDisplay.getDisplayMode().getWidth() == FRAME_FHD_SIZE_WIDTH )
		//			{
		//				img = new ImageIcon( ImageConstants.DIR_DPI_HD + imgPath ).getImage();
		//			}
		//			if( _gameDisplay.getDisplayMode().getWidth() == FRAME_UHD_SIZE_WIDTH )
		//			{
		//				img = new ImageIcon( ImageConstants.DIR_DPI_FHD + imgPath ).getImage();
		//			}
		//		}

		return img;
	}

	/**
	 * 스크린모드를 변경
	 * @param isFullScreen 현재 풀스크린인지
	 */
	protected void changeScreenMode( boolean isFullScreen )
	{
		if( isFullScreen )
		{
			// 게임 화면 위치가 서브 모니터에 있을때, 서브모니터 상의 전체화면을 해제
			dispose();
			_gameDisplay.setFullScreenWindow( null );
		}
		else
		{
			// 게임 화면 위치가 서브 모니터에 있을때, 서브모니터 상의 전체화면을 해제
			dispose();
			_gameDisplay.setFullScreenWindow( this );

		}
		setVisible( true );
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
	private class GameKeyListener extends KeyAdapter
	{

		/** 입력된 키코드 해시셋*/
		private HashSet<Integer> _keyCodeSet = new HashSet<Integer>();

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
	}

	/**
	 * 게임화면 모니터간 이동 감시 리스너
	 * @author CheonMungi
	 *
	 */
	protected class WindowMoveListener extends ComponentAdapter
	{
		/** 서브 디스플레이로 이동했는지*/
		private boolean _isMoved2Sub = false;

		@Override
		public void componentMoved( ComponentEvent e )
		{
			if( getLocation().x <= _dsplayLocationX && !_isMoved2Sub || _isFullScreen )
			{
				// 게임 화면이 메인 디스플레이에 있는 경우는 그대로 리턴
				return;
			}

			if( getLocation().x > _dsplayLocationX && _subDisplay != null )
			{
				if( !_isMoved2Sub )
				{
					// 게임 화면이 서브디스플레이로 이동한 경우
					_gameDisplay = _ge.getScreenDevices()[ 1 ];
					_subDisplay = _ge.getDefaultScreenDevice();

					_isMoved2Sub = true;
				}
			}
			if( getLocation().x <= _dsplayLocationX && _subDisplay != null )
			{
				if( _isMoved2Sub )
				{
					// 게임 화면이 서브 디스플레이에서 메인 디스플레이로 되돌아간 경우
					_gameDisplay = _ge.getDefaultScreenDevice();
					_subDisplay = _ge.getScreenDevices()[ 1 ];

					_isMoved2Sub = false;
				}
			}
		}
	}
}

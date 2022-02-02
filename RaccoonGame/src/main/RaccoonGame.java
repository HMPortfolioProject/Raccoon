package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.*;

import constants.LabelConstants;

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
	/** 이미지 : 스테이지*/
	private Image _stageImg = new ImageIcon( "src/image/stage/BackGround1.png" ).getImage();
	/** 스테이지 사이즈*/
	private int _stageWidth = 0;
	private int _stageHeight = 0;

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

		// 초기 사이즈 저장
		_stageWidth = _stageImg.getWidth( null );
		_stageHeight = _stageImg.getHeight( null );

		// Alt + Enter키로 풀스크린, 창모드 전환하는 키 리스너 추가
		addKeyListener( new _FullScreenKeyListener() );

		setVisible( true );
		setLayout( null );
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
	 * @param graphic
	 */
	private void _screenDraw( Graphics graphic )
	{
		if( _stageWidth != getSize().width && _stageHeight != getSize().height )
		{
			// 스테이지 사이즈와 현재 사이즈가 일치 하지 않을경우 해상도가 변경된 것으로 간주
			// 풀스크린, 창모드 전환시 이미지 크기 조정
			_reSizingStage();

			_stageWidth = getSize().width;
			_stageHeight = getSize().height;
		}
		else
		{
			graphic.drawImage( _stageImg, 0, 0, null );
		}
		repaint();
	}

	/**
	 * 이미지 사이즈를 재조정
	 */
	private void _reSizingStage()
	{
		try
		{
			// TODO : path 수정, 스테이지 바뀌는걸 염두해서 재수정 필요
			String path = "src/image/stage/BackGround1.png";
			File file = new File( path );
			Image originStage = ImageIO.read( file );

			Image resizeStage = originStage.getScaledInstance( getSize().width, getSize().height, Image.SCALE_SMOOTH );

			BufferedImage newStage = new BufferedImage( getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB );

			Graphics g = newStage.getGraphics();
			g.drawImage( resizeStage, 0, 0, null );

			//임시 파일로 생성
			File temp = File.createTempFile( file.getName(), "png" );
			ImageIO.write( newStage, "png", temp );
			// 프로그램 종료시 임시 파일 삭제
			temp.deleteOnExit();

			_stageImg = new ImageIcon( temp.getAbsolutePath() ).getImage();

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
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
	 * 풀스크린, 창모드 전환 키리스너
	 * @author CheonMungi
	 *
	 */
	private class _FullScreenKeyListener implements KeyListener
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
			}
		}

		@Override
		public void keyTyped( KeyEvent e )
		{
			// TODO Auto-generated method stub
		}
	}
}

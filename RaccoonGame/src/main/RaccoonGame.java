package main;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

import javax.swing.JFrame;

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

			setSize( width / 2, height / 2 );

			setLocation( ( ( width / 2 ) - ( getSize().width / 2 ) ) + gds[ 0 ].getDefaultConfiguration().getBounds().x,
				( ( height / 2 ) - ( getSize().height / 2 ) ) + gds[ 0 ].getDefaultConfiguration().getBounds().y );
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			setVisible( true );
		}
		else
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow( this );
		}

		// Alt + Enter키로 풀스크린, 창모드 전환하는 키 리스너 추가
		addKeyListener( new _FullScreenKeyListener() );

		setVisible( true );
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
			}
		}

		@Override
		public void keyTyped( KeyEvent e )
		{
			// TODO Auto-generated method stub
		}
	}
}

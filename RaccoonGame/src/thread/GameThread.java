package thread;

import java.awt.*;
import java.util.*;

import javax.swing.ImageIcon;

import constants.ImageConstants;
import main.RaccoonGame;
import obj.enemy.*;
import obj.item.Gimlet;

/**
 * 게임 전반적인 진행을 지원하는 스레드 클래스
 * @author CheonMungi
 *
 */
public class GameThread extends Thread
{
	/** 압정 기본 Y좌표*/
	private static int ITEM_GIMLET_DEFAULT_LOCATION_Y = 100;
	/** 게임의 딜레이, 딜레이마다 증가*/
	private int _delay = 20;
	private long _pretime = 0;

	/** 적 캐릭터 */
	private EnemyAbst[][] _enemyList = new EnemyAbst[ 5 ][ 2 ];

	/** 캐릭터 사망했는지*/
	private boolean isDead = false;
	/** 모든 생명 다 소진하고 캐릭터 사망했는지*/
	private boolean _isOver = false;
	ArrayList<Gimlet> _gimletList = new ArrayList<Gimlet>();

	@Override
	public void run()
	{
		while( true )
		{
			_pretime = System.currentTimeMillis();

			if( System.currentTimeMillis() - _pretime < _delay )
			{
				try
				{
					Thread.sleep( _delay - System.currentTimeMillis() + _pretime );
				}
				catch( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public synchronized void start()
	{
		super.start();

		craeteEnemy();
		createItem();
	}

	/**
	 * 적 캐릭터 생성
	 */
	public void craeteEnemy()
	{
		// TODO : 스테이지 클래스 완성되면 옮겨야 함.(스테이지 마다 위치를 다르게 주기 위해)

		// 2차원 배열을 이용해서 적 캐릭터 랜덤 배치
		ArrayList<EnemyAbst> enemyList = new ArrayList<EnemyAbst>();
		enemyList.add( new EnemyOrangeCentipede( 0, 0 ) );
		enemyList.add( new EnemyGreenCentipede( 0, 0 ) );
		enemyList.add( new EnemyGreenCentipede( 0, 0 ) );
		enemyList.add( new EnemyGreenCentipede( 0, 0 ) );
		enemyList.add( new EnemyGreenCentipede( 0, 0 ) );

		Random r = new Random();
		for( int ii = 0; ii < enemyList.size(); ii++ )
		{
			int index = r.nextInt( enemyList.size() );
			EnemyAbst enemy = enemyList.get( index );
			for( int jj = 0; jj < ii; jj++ )
			{
				if( enemy.equals( enemyList.get( jj ) ) )
				{
					ii--;
				}
			}
		}

		for( int ii = 0; ii < enemyList.size(); ii++ )
		{
			EnemyAbst enemy = enemyList.get( ii );
			_addEnemy( enemy );
		}

		// Y좌표 설정
		ArrayList<Double> yList = new ArrayList<Double>();
		yList.add( Double.valueOf( 220 ) );
		yList.add( Double.valueOf( 360 ) );
		yList.add( Double.valueOf( 500 ) );
		yList.add( Double.valueOf( 640 ) );
		yList.add( Double.valueOf( 780 ) );

		for( int ii = 0; ii < _enemyList.length; ii++ )
		{
			EnemyAbst[] floor = _enemyList[ ii ];
			double y = yList.get( ii ).doubleValue();

			_setLocationYForEnemy( floor, y );
		}
	}

	/**
	 * 적 캐릭터를 배열에 추가합니다
	 * @param enemy
	 */
	private void _addEnemy( EnemyAbst enemy )
	{
		int x = (int) ( Math.random() * 2 );
		int y = (int) ( Math.random() * 5 );

		if( _enemyList[ y ][ x ] != null )
		{
			_addEnemy( enemy );
			return;
		}

		int minX = 95;
		int maxX = 1351;
		int locationX = (int) ( Math.random() * ( maxX - minX + 1 ) + minX );

		enemy.setLocationX( locationX );

		_enemyList[ y ][ x ] = enemy;
	}

	/**
	 * 적 케릭터 Y좌표 설정
	 * @param floor 층
	 * @param locationY Y좌표
	 */
	private void _setLocationYForEnemy( EnemyAbst[] floor, double locationY )
	{
		for( int jj = 0; jj < floor.length; jj++ )
		{
			EnemyAbst enemy = floor[ jj ];

			if( enemy == null || enemy.getLocationY() > 0 )
			{
				continue;
			}

			enemy.setLocationY( locationY );
			enemy.setDefaultLocationY( locationY );

		}

	}

	/**
	 * 압정, 랜덤주머니 생성
	 */
	public void createItem()
	{
		// TODO : 스테이지 클래스 완성되면 옮겨야 함.(스테이지 마다 위치를 다르게 주기 위해)
		int locationX = 300;
		int locationY = ITEM_GIMLET_DEFAULT_LOCATION_Y;

		for( int ii = 0; ii < 2; ii++ )
		{
			if( ii % 2 == 0 )
			{
				_gimletList.add( new Gimlet( locationX, locationY += 137 ) );
				_gimletList.add( new Gimlet( locationX + 700, locationY += 137 ) );
				_gimletList.add( new Gimlet( locationX + 500, locationY += 137 ) );
			}
			else
			{
				_gimletList.add( new Gimlet( locationX + 300, locationY += 137 ) );
				_gimletList.add( new Gimlet( locationX + 800, locationY += 137 ) );
				_gimletList.add( new Gimlet( locationX + 100, locationY ) );
				_gimletList.add( new Gimlet( locationX + 700, locationY ) );
			}

		}
	}

	/**
	 * 이미지 그리기
	 * @param g
	 */
	public void gameDraw( Graphics g, boolean isFullScreen )
	{
		// TODO : 스테이지 클래스 완성되면 옮겨야 함.(스테이지 마다 위치를 다르게 주기 위해)
		int displayWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();

		for( int ii = 0; ii < _enemyList.length; ii++ )
		{
			EnemyAbst[] floor = _enemyList[ ii ];
			for( int jj = 0; jj < floor.length; jj++ )
			{
				EnemyAbst enemy = floor[ jj ];

				if( enemy == null )
				{
					continue;
				}

				//오브젝트 X좌표 최대값
				int maxLocationX = 1350;

				if( isFullScreen )
				{
					if( displayWidth == RaccoonGame.FRAME_UHD_SIZE_WIDTH )
					{
						// UHD대응(풀스크린)
						_resizedImage( enemy, ImageConstants.DIR_DPI_UHD );
						maxLocationX *= 2.4;
					}
					else
					{
						// FHD대응(풀스크린)
						_resizedImage( enemy, ImageConstants.DIR_DPI_FHD );
						maxLocationX *= 1.2;
					}
				}
				else
				{
					if( displayWidth == RaccoonGame.FRAME_UHD_SIZE_WIDTH )
					{
						// UHD대응(창모드)
						_resizedImage( enemy, ImageConstants.DIR_DPI_FHD );
						maxLocationX *= 1.2;
					}
					else
					{
						// FHD대응(창모드)
						_resizedImage( enemy, ImageConstants.DIR_DPI_HD );
					}
				}
				enemy.executeMove( g, maxLocationX );
			}
		}

		Iterator<Gimlet> gimletIter = _gimletList.iterator();
		while( gimletIter.hasNext() )
		{
			Gimlet gimlet = gimletIter.next();
			g.drawImage( gimlet.getImage(), (int)gimlet.getLocationX(), (int)gimlet.getLocationY(), null );
		}
	}

	/**
	 * DPI에 맞춰 오브젝트 속성을 재설정 합니다.
	 * @param isNotFullScreen
	 */
	public void reSettingObjectProp( boolean isNotFullScreen )
	{
		int displayWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();

		for( int ii = 0; ii < _enemyList.length; ii++ )
		{
			EnemyAbst[] floor = _enemyList[ ii ];
			for( int jj = 0; jj < floor.length; jj++ )
			{
				EnemyAbst enemy = floor[ jj ];

				if( enemy == null )
				{
					continue;
				}

				if( !isNotFullScreen )
				{
					if( displayWidth == RaccoonGame.FRAME_UHD_SIZE_WIDTH )
					{
						// UHD대응(풀스크린)
						enemy.setLocationY( enemy.getLocationY() * 2.2 );
						_reSettingSpeedForObject( enemy, RaccoonGame.FRAME_UHD_SIZE_WIDTH );
					}
					else
					{
						// FHD대응(풀스크린)
						enemy.setLocationY( enemy.getLocationY() * 1.1 );
						_reSettingSpeedForObject( enemy, RaccoonGame.FRAME_FHD_SIZE_WIDTH );
					}
				}
				else
				{
					if( displayWidth == RaccoonGame.FRAME_UHD_SIZE_WIDTH )
					{
						// UHD대응(창모드)
						enemy.setLocationY( enemy.getLocationY() * 1.1 );
						_reSettingSpeedForObject( enemy, RaccoonGame.FRAME_FHD_SIZE_WIDTH );
					}
					else
					{
						// FHD대응(창모드)
						enemy.setLocationY( enemy.getDefaultLocationY() );
						_reSettingSpeedForObject( enemy, RaccoonGame.FRAME_HD_SIZE_WIDTH );
					}
				}
			}
		}

		Iterator<Gimlet> gimletIter = _gimletList.iterator();

		while( gimletIter.hasNext() )
		{
			Gimlet gimlet = gimletIter.next();

			if( !isNotFullScreen )
			{
				if( displayWidth == RaccoonGame.FRAME_UHD_SIZE_WIDTH )
				{
					// UHD대응(풀스크린)
					gimlet.setLocationX( gimlet.getLocationX() * 2.2 );
					gimlet.setLocationY( gimlet.getLocationY() * 2.2 );
				}
				else
				{
					// FHD대응(풀스크린)
					gimlet.setLocationX( gimlet.getLocationX() * 1.1 );
					gimlet.setLocationY( gimlet.getLocationY() * 1.1 );
				}
			}
			else
			{
				if( displayWidth == RaccoonGame.FRAME_UHD_SIZE_WIDTH )
				{
					// UHD대응(창모드)
					gimlet.setLocationX( gimlet.getLocationX() * 1.1 );
					gimlet.setLocationY( gimlet.getLocationY() * 1.1 );
				}
				else
				{
					// FHD대응(창모드)
					gimlet.setLocationX( gimlet.getDefaultLocationX() );
					gimlet.setLocationY( gimlet.getDefaultLocationY() );
				}
			}
		}
	}

	/**
	 * DPI에 맞춰 적 캐릭터 이동속도를 재설정 합니다.
	 * @param enemy
	 * @param dpiWidth
	 */
	private void _reSettingSpeedForObject( EnemyAbst enemy, int dpiWidth )
	{
		if( dpiWidth == RaccoonGame.FRAME_UHD_SIZE_WIDTH )
		{
			// UHD대응(풀스크린)
			enemy.setSpeed( enemy.getSpeed() * 2.2 );

		}
		else if( dpiWidth == RaccoonGame.FRAME_FHD_SIZE_WIDTH )
		{
			// UHD대응(창모드), FHD대응(풀스크린)
			enemy.setSpeed( enemy.getSpeed() * 1.1 );
		}
		else
		{
			// FHD대응(창모드)
			enemy.setSpeed( enemy.getDefaultSpeed() );
		}
	}

	/**
	 * 적 케릭터 이미지를 해상도에 맞게 지정합니다
	 * @param enemy 적 케릭터
	 * @param windowDPI 해상도 사이즈
	 */
	private void _resizedImage( EnemyAbst enemy, String windowDPI )
	{
		switch( enemy.getEnemyENUM() )
		{
			case GREEN_CENTIPEDE:
				enemy.setLeftDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_GREEN_CENTIPEDE_DOWN ).getImage() );
				enemy.setLeftUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_GREEN_CENTIPEDE_UP ).getImage() );
				enemy.setRightDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_GREEN_CENTIPEDE_DOWN ).getImage() );
				enemy.setRightUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_GREEN_CENTIPEDE_UP ).getImage() );

				break;
			case ORANGE_CENTIPEDE:
				enemy.setLeftDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_ORANGE_CENTIPEDE_DOWN ).getImage() );
				enemy.setLeftUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_ORANGE_CENTIPEDE_UP ).getImage() );
				enemy.setRightDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_ORANGE_CENTIPEDE_DOWN ).getImage() );
				enemy.setRightUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_ORANGE_CENTIPEDE_UP ).getImage() );
				break;
			case RED_CENTIPEDE:
				enemy.setLeftDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_RED_CENTIPEDE_DOWN ).getImage() );
				enemy.setLeftUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_RED_CENTIPEDE_UP ).getImage() );
				enemy.setRightDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_RED_CENTIPEDE_DOWN ).getImage() );
				enemy.setRightUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_RED_CENTIPEDE_UP ).getImage() );
				break;
			case GREEN_SNAKE:
				enemy.setLeftDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_GREEN_SNAKE_DOWN ).getImage() );
				enemy.setLeftUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_GREEN_SNAKE_UP ).getImage() );
				enemy.setRightDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_GREEN_SNAKE_DOWN ).getImage() );
				enemy.setRightUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_GREEN_SNAKE_UP ).getImage() );
				break;
			case ORANGE_SNAKE:
				enemy.setLeftDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_ORANGE_SNAKE_DOWN ).getImage() );
				enemy.setLeftUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_ORANGE_SNAKE_UP ).getImage() );
				enemy.setRightDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_ORANGE_SNAKE_DOWN ).getImage() );
				enemy.setRightUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_ORANGE_SNAKE_UP ).getImage() );
				break;
			case RED_SNAKE:
				enemy.setLeftDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_RED_SNAKE_DOWN ).getImage() );
				enemy.setLeftUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_LEFT_RED_SNAKE_UP ).getImage() );
				enemy.setRightDownImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_RED_SNAKE_DOWN ).getImage() );
				enemy.setRightUpImage( new ImageIcon( windowDPI + ImageConstants.ENEMY_RIGHT_RED_SNAKE_UP ).getImage() );
				break;
		}
	}

	private void enemyProcess()
	{
		// TODO : 너구리와 닿을시 생명갯수 차감, 게임오버 판정 처리 필요
	}

}

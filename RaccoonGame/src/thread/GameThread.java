package thread;

import java.awt.Graphics;
import java.util.*;

import obj.enemy.*;
import obj.item.Gimlet;

/**
 * 게임 전반적인 진행을 지원하는 스레드 클래스
 * @author CheonMungi
 *
 */
public class GameThread extends Thread
{
	/** 적 캐릭터 기본 X좌표*/
	private static int ENEMY_DEFAULT_LOCATION_X = 120;
	/** 적 캐릭터 기본 Y좌표*/
	private static int ENEMY_DEFAULT_LOCATION_Y = 80;
	/** 압정 기본 Y좌표*/
	private static int ITEM_GIMLET_DEFAULT_LOCATION_Y = 100;
	/** 게임의 딜레이, 딜레이마다 증가*/
	private int _delay = 20;
	private long _pretime = 0;

	/** 적 캐릭터 리스트*/
	private ArrayList<EnemyAbst> _enemyList = new ArrayList<EnemyAbst>();

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
		int locationX = ENEMY_DEFAULT_LOCATION_X;
		int locationY = ENEMY_DEFAULT_LOCATION_Y;
		for( int ii = 0; ii < 2; ii++ )
		{
			if( ii % 2 == 0 )
			{
				_enemyList.add( new EnemyGreenCentipede( 1350, locationY += 140 ) );
				_enemyList.add( new EnemyGreenSnake( locationX, locationY += 140 ) );
				_enemyList.add( new EnemyRedCentipede( 800, locationY ) );
			}
			else
			{
				_enemyList.add( new EnemyOrangeCentipede( locationX, locationY += 140 ) );
				_enemyList.add( new EnemyOrangeSnake( 1350, locationY += 140 ) );
				_enemyList.add( new EnemyRedSnake( 800, locationY ) );
			}

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
				_gimletList.add( new Gimlet( locationX, locationY += 140 ) );
				_gimletList.add( new Gimlet( locationX + 700, locationY += 140 ) );
				_gimletList.add( new Gimlet( locationX + 500, locationY += 140 ) );
			}
			else
			{
				_gimletList.add( new Gimlet( locationX + 300, locationY += 140 ) );
				_gimletList.add( new Gimlet( locationX + 800, locationY += 140 ) );
				_gimletList.add( new Gimlet( locationX + 100, locationY ) );
				_gimletList.add( new Gimlet( locationX + 700, locationY ) );
			}

		}
	}

	/**
	 * 이미지 그리기
	 * @param g
	 */
	public void gameDraw( Graphics g )
	{
		// TODO : 스테이지 클래스 완성되면 옮겨야 함.(스테이지 마다 위치를 다르게 주기 위해)
		Iterator<EnemyAbst> enemyIter = _enemyList.iterator();
		while( enemyIter.hasNext() )
		{
			EnemyAbst enemy = enemyIter.next();
			enemy.executeMove( g );
		}

		Iterator<Gimlet> gimletIter = _gimletList.iterator();
		while( gimletIter.hasNext() )
		{
			Gimlet gimlet = gimletIter.next();
			g.drawImage( gimlet.getImage(), gimlet.getLocationX(), gimlet.getLcationY(), null );
		}
	}

	private void enemyProcess()
	{
		// TODO : 너구리와 닿을시 생명갯수 차감, 게임오버 판정 처리 필요
	}
}

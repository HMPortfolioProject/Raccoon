package obj.enemy;

import java.awt.Image;

import constants.LabelConstants;

/**
 * 적 캐릭터정보를 관리하는 클래스
 * @author CheonMungi
 *
 */
public abstract class EnemyAbst
{
	/** X좌표*/
	private long _locationX = 0;
	/** Y좌표*/
	private long _locationY = 0;
	/** 오른쪽으로 움직이는지*/
	private boolean _isRight = true;
	/** 왼쪽으로 움직이는지*/
	private boolean _isLeft = false;

	/**
	 * 컨스트럭터
	 * @param locationX X좌표
	 * @param locationY Y좌표
	 */
	public EnemyAbst( int locationX, int locationY )
	{
		_locationX = locationX;
		_locationY = locationY;

	}

	/**
	 * 적 캐릭터의 이동을 실행
	 */
	public void executeMove()
	{
		if( getEnemyName().equals( LabelConstants.GREEN_CENTIPEDE ) )
		{
			_move( 5 );
		}
		else if( getEnemyName().equals( LabelConstants.ORANGE_CENTIPEDE ) )
		{
			_move( 7.5 );
		}
		else if( getEnemyName().equals( LabelConstants.RED_CENTIPEDE ) )
		{
			_move( 10 );
		}
		else if( getEnemyName().equals( LabelConstants.GREEN_SNAKE ) )
		{
			_move( 7.5 );
		}
		else if( getEnemyName().equals( LabelConstants.ORANGE_SNAKE ) )
		{
			_move( 9.475 );
		}
		else
		{
			_move( 12.5 );
		}
	}

	/**
	 * 캐릭터 이동
	 * @param speed 이동 속도
	 */
	private void _move( double speed )
	{
		if( _locationX <= 51 )
		{
			_isRight = true;
			_isLeft = false;
		}
		if( _locationX >= 690 )
		{
			_isRight = false;
			_isLeft = true;
		}
		if( _isRight )
		{
			_locationX += speed;
		}
		if( _isLeft )
		{
			_locationX -= speed;
		}
	}

	/**
	 * 적 캐릭터 오른쪽 방향 첫번째 이미지 가져옵니다.
	 * @return 적 캐릭터 이미지
	 */
	public abstract Image getRightDownImage();

	/**
	 * 적 캐릭터 오른쪽 방향 두번째 이미지 가져옵니다.
	 * @return 적 캐릭터 이미지
	 */
	public abstract Image getRightUpImage();

	/**
	 * 적 캐릭터 왼쪽 방향 첫번째 이미지 가져옵니다.
	 * @return 적 캐릭터 이미지
	 */
	public abstract Image getLeftDownImage();

	/**
	 * 적 캐릭터 왼쪽 방향 두번째 이미지 가져옵니다.
	 * @return 적 캐릭터 이미지
	 */
	public abstract Image getLeftUpImage();

	/**
	 * 적 캐릭터 이름을 가져옵니다.
	 * @return 적 캐릭터 이름
	 */
	public abstract String getEnemyName();

	/**
	 * 넓이를 가져옵니다.
	 * @return 넓이
	 */
	public abstract int getWidth();

	/**
	 * 높이를 가져옵니다.
	 * @return 높이
	 */
	public abstract int getHeight();

}

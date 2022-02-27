package obj.enemy;

import java.awt.*;

/**
 * 적 캐릭터정보를 관리하는 클래스
 * @author CheonMungi
 *
 */
public abstract class EnemyAbst
{
	/** X좌표*/
	private int _locationX = 0;
	/** Y좌표*/
	private int _locationY = 0;
	/** 오른쪽으로 움직이는지*/
	private boolean _isRight = true;
	/** 왼쪽으로 움직이는지*/
	private boolean _isLeft = false;

	private boolean _isChange = false;

	protected Image rightDownImage = null;
	protected Image rightUpImage = null;
	protected Image leftDownImage = null;
	protected Image leftUpImage = null;

	protected GraphicsDevice defaultSd = null;

	/**
	 * 컨스트럭터
	 * @param locationX X좌표
	 * @param locationY Y좌표
	 */
	public EnemyAbst( int locationX, int locationY )
	{
		_locationX = locationX;
		_locationY = locationY;
		defaultSd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	}

	/**
	 * 적 캐릭터의 이동을 실행
	 * @param g 그래픽
	 */
	public void executeMove( Graphics g )
	{
		double speed = getSpeed();
		_move( g, speed );
	}

	/**
	 * 캐릭터 이동
	 * @param g 그래픽
	 * @param speed 이동 속도
	 */
	private void _move( Graphics g, double speed )
	{
		if( _locationX <= 95 )
		{
			_isRight = true;
			_isLeft = false;
		}
		if( _locationX >= 1350 )
		{
			_isRight = false;
			_isLeft = true;
		}
		if( _isRight )
		{
			_locationX += speed;
			if( !_isChange )
			{
				g.drawImage( getRightDownImage(), getLocationX(), getLcationY(), null );
				_isChange = true;
			}
			else
			{
				g.drawImage( getRightUpImage(), getLocationX(), getLcationY(), null );
				_isChange = false;
			}

		}
		if( _isLeft )
		{
			_locationX -= speed;
			if( !_isChange )
			{
				g.drawImage( getLeftDownImage(), getLocationX(), getLcationY(), null );
				_isChange = true;
			}
			else
			{
				g.drawImage( getLeftUpImage(), getLocationX(), getLcationY(), null );
				_isChange = false;
			}
		}
	}

	/**
	 * X좌표를 가져옵니다.
	 * @return
	 */
	public int getLocationX()
	{
		return _locationX;
	}

	/**
	 * X좌표를 설정합니다.
	 * @param locationX X좌표
	 */
	public void setLocationX( int locationX )
	{
		_locationX = locationX;
	}

	/**
	 * Y좌표를 가져옵니다.
	 * @return
	 */
	public int getLcationY()
	{
		return _locationY;
	}

	/**
	 * Y좌표를 설정합니다.
	 * @param locationY Y좌표
	 */
	public void setLocationY( int locationY )
	{
		_locationY = locationY;
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
	 * 적 캐릭터 오른쪽 방향 첫번째 이미지를 설정합니다.
	 * @param img 이미지
	 */
	public abstract void setRightDownImage( Image img );

	/**
	 * 적 캐릭터 오른쪽 방향 두번째 이미지를 설정합니다.
	 * @param img 이미지
	 */
	public abstract void setRightUpImage( Image img );

	/**
	 * 적 캐릭터 왼쪽 방향 첫번째 이미지를 설정합니다.
	* @param img 이미지
	 */
	public abstract void setLeftDownImage( Image img );

	/**
	 * 적 캐릭터 왼쪽 방향 두번째 이미지를 설정합니다.
	 * @param img 이미지
	 */
	public abstract void setLeftUpImage( Image img );

	/**
	 * 적 캐릭터 이름을 가져옵니다.
	 * @return 적 캐릭터 이름
	 */
	public abstract EnemyEnum getEnemyENUM();

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

	/**
	 * 이동 속도를 가져옵니다.
	 * @return 이동 속도
	 */
	public abstract double getSpeed();

	/**
	 * 적 케릭터 enum클래스
	 * @author CheonMungi
	 *
	 */
	public enum EnemyEnum
	{
			/** ENUM : 초록 지네*/
			GREEN_CENTIPEDE("Green Centipede"),
			/** ENUM : 주황 지네*/
			ORANGE_CENTIPEDE("Orange Centipede"),
			/** ENUM : 빨강 지네*/
			RED_CENTIPEDE("Red Centipede"),
			/** ENUM : 초록 뱀*/
			GREEN_SNAKE("Green Snake"),
			/** ENUM : 주황 뱀*/
			ORANGE_SNAKE("Orange Snake"),
			/** ENUM : 빨강 뱀*/
			RED_SNAKE("Red Snake");

		/**
		 * 값
		 */
		private final String _value;

		/**
		 * 컨스트럭터
		 * @param value 값
		 */
		EnemyEnum( String value )
		{
			this._value = value;
		}

		/**
		 * enum값을 가져옵니다
		 * @return
		 */
		public String getValue()
		{
			return _value;
		}
	}
}

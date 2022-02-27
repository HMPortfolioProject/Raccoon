package obj.item;

import java.awt.Image;

import javax.swing.ImageIcon;

import constants.ImageConstants;

/**
 * 압정 클래스
 * @author CheonMungi
 *
 */
public class Gimlet
{
	/** X좌표*/
	private int _locationX = 0;
	/** Y좌표*/
	private int _locationY = 0;
	/** 압정 이미지*/
	private Image _img = new ImageIcon( ImageConstants.ENEMY_GIMLET ).getImage();
	/** 이미지 가로 사이즈 */
	private int _width = _img.getWidth( null );
	/** 이미지 세로 사이즈*/
	private int _height = _img.getHeight( null );

	/**
	 * 컨스트럭터
	 * @param locationX X좌표
	 * @param locationY Y좌표
	 */
	public Gimlet( int locationX, int locationY )
	{
		_locationX = locationX;
		_locationY = locationY;
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
	 * X좌표를 가져옵니다.
	 * @return X좌표
	 */
	public int getLocationX()
	{
		return _locationX;
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
	 * Y좌표를 가져옵니다.
	 * @return Y좌표
	 */
	public int getLcationY()
	{
		return _locationY;
	}

	/**
	 * 이미지를 가져옵니다.
	 * @return 압정 이미지
	 */
	public Image getImage()
	{
		return _img;
	}

	/**
	 * 이미지 가로 사이즈를 설정합니다.
	 * @param width 가로 사이즈
	 */
	public void setWidth( int width )
	{
		_width = width;
	}

	/**
	 * 이미지 가로 사이즈를 가져옵니다.
	 * @return 이미지 가로 사이즈
	 */
	public int getWidth()
	{
		return _width;
	}

	/**
	 * 이미지 세로 사이즈를 설정합니다.
	 * @param height 세로 사이즈
	 */
	public void setHeight( int height )
	{
		_height = height;
	}

	/**
	 * 이미지 세로 사이즈를 가져옵니다.
	 * @return 이미지 세로 사이즈
	 */
	public int getHeight()
	{
		return _height;
	}
}

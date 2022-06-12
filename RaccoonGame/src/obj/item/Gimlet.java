package obj.item;

import java.awt.*;

import javax.swing.ImageIcon;

import constants.ImageConstants;
import main.RaccoonGame;

/**
 * 압정 클래스
 * @author CheonMungi
 *
 */
public class Gimlet
{
	/** X좌표*/
	private double _locationX = 0;
	/** Y좌표*/
	private double _locationY = 0;
	/** X좌표 초기값*/
	private double _defaultLocationX = 0;
	/** Y좌표 초기값*/
	private double _defaultLocationY = 0;

	/** 압정 이미지*/
	private Image _img = null;
	/** 이미지 가로 사이즈 */
	private int _width = 0;
	/** 이미지 세로 사이즈*/
	private int _height = 0;

	/**
	 * 컨스트럭터
	 * @param locationX X좌표
	 * @param locationY Y좌표
	 */
	public Gimlet( int locationX, int locationY )
	{
		_locationX = locationX;
		_locationY = locationY;
		_defaultLocationX = locationX;
		_defaultLocationY = locationY;

		GraphicsDevice defaultSd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		if( defaultSd.getDisplayMode().getWidth() == RaccoonGame.FRAME_FHD_SIZE_WIDTH )
		{
			_img = new ImageIcon( ImageConstants.DIR_DPI_FHD + ImageConstants.ENEMY_GIMLET ).getImage();
		}
		if( defaultSd.getDisplayMode().getWidth() == RaccoonGame.FRAME_UHD_SIZE_WIDTH )
		{
			_img = new ImageIcon( ImageConstants.DIR_DPI_UHD + ImageConstants.ENEMY_GIMLET ).getImage();
		}

		_width = _img.getWidth( null );
		_height = _img.getHeight( null );
	}

	/**
	 * X좌표를 설정합니다.
	 * @param locationX X좌표
	 */
	public void setLocationX( double locationX )
	{
		_locationX = locationX;
	}

	/**
	 * X좌표를 가져옵니다.
	 * @return X좌표
	 */
	public double getLocationX()
	{
		return _locationX;
	}

	/**
	 * Y좌표를 설정합니다.
	 * @param locationY Y좌표
	 */
	public void setLocationY( double locationY )
	{
		_locationY = locationY;
	}

	/**
	 * Y좌표를 가져옵니다.
	 * @return Y좌표
	 */
	public double getLocationY()
	{
		return _locationY;
	}

	/**
	 * X좌표의 초기값을 가져옵니다.
	 * @return X좌표 초기값
	 */
	public double getDefaultLocationX()
	{
		return _defaultLocationX;
	}

	/**
	 * Y좌표의 초기값을 가져옵니다.
	 * @return Y좌표 초기값
	 */
	public double getDefaultLocationY()
	{
		return _defaultLocationY;
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

package logic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 이미지 사이즈를 조절하는 로직 클래스
 * @author CheonMungi
 *
 */
public class ReSizingImageLogic
{
	@Deprecated
	private ReSizingImageLogic()
	{
	}

	/**
	 * 이미지 사이즈를 조정합니다.
	 * @param frame 게임 화면
	 * @param imgMap 이미지 맵
	 * @param targetImgPath 이미지 경로
	 * @return 사이즈 조정된 이미지
	 */
	public static Image reSizingImg( JFrame frame, HashMap<String, File> imgMap, String targetImgPath )
	{
		Image result = null;
		try
		{
			// TODO : path 수정, 스테이지 바뀌는걸 염두해서 재수정 필요
			// TODO : 스테이지가 바뀔시 현재 스테이지 이미지 경로를 가져와 path에 대입할수 잇도록 수정 필요
			File file = new File( targetImgPath );

			String replacedName = _repalceFileName( frame, file.getName(), "png" );

			if( !imgMap.isEmpty() && imgMap.containsKey( replacedName ) )
			{
				File temp = imgMap.get( replacedName );

				result = new ImageIcon( temp.getAbsolutePath() ).getImage();
			}
			else
			{
				Image originImg = ImageIO.read( file );

				Image resizedImg = originImg.getScaledInstance( frame.getSize().width, frame.getSize().height, Image.SCALE_SMOOTH );

				BufferedImage newImg = new BufferedImage( frame.getSize().width, frame.getSize().height, BufferedImage.TYPE_INT_RGB );

				Graphics g = newImg.getGraphics();
				g.drawImage( resizedImg, 0, 0, null );

				//임시 파일로 생성
				File temp = new File( System.getProperty( "java.io.tmpdir" ) + "\\" + replacedName );
				ImageIO.write( newImg, "png", temp );

				imgMap.put( replacedName, temp );
				// 프로그램 종료시 임시 파일 삭제
				temp.deleteOnExit();

				result = new ImageIcon( temp.getAbsolutePath() ).getImage();
			}

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 임시 파일 이름으로 변경
	 * @param originName
	 * @param extension
	 * @return
	 */
	private static String _repalceFileName( JFrame frame, String originName, String extension )
	{
		StringBuffer sb = new StringBuffer();
		sb.append( _getImgName( originName ) );
		sb.append( "_" );
		sb.append( String.valueOf( frame.getSize().width ) ).append( "X" ).append( String.valueOf( frame.getSize().height ) );
		sb.append( "." + extension );

		return sb.toString();
	}

	/**
	 * 확장자를 제외한 파일 이름 가져오기
	 * @param name
	 * @return
	 */
	private static String _getImgName( String name )
	{
		return name.substring( 0, name.length() - 4 );
	}
}

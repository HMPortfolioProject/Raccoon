package constants;

import java.io.File;

/**
 * 게임 스테이지, 캐릭터, 오브젝트 이미지 경로를 관리하는 클래스
 * @author CheonMungi
 *
 */
public class ImageConstants
{

	/**이미지 디렉토리*/
	public static String RACCOON_IAMGE_DIRECTORY = new File( System.getProperty( "user.dir" ) ).getParentFile().getAbsolutePath() + "\\image";

	/***************************/
	/********해상도별 디렉토리********/
	/***************************/
	/** HD*/
	public static String DIR_DPI_HD = RACCOON_IAMGE_DIRECTORY + "\\HD";
	/** FHD*/
	public static String DIR_DPI_FHD = RACCOON_IAMGE_DIRECTORY + "\\FHD";
	/** UHD(4K)*/
	public static String DIR_DPI_UHD = RACCOON_IAMGE_DIRECTORY + "\\UHD";

	/*****************************/
	/********카테고리별 디렉토리********/
	/****************************/
	/** 너구리*/
	public static String DIR_RACCOON = "\\raccoon";
	/** 적 캐릭터*/
	public static String DIR_ENEMY = "\\enemy";
	/** 음식*/
	public static String DIR_FOOD = "\\food";
	/** 아이템*/
	public static String DIR_ITEM = "\\item";
	/** 스테이지*/
	public static String DIR_STAGE = "\\stage";
	/** 시작*/
	public static String DIR_START = "\\start";

	/************************/
	/********너구리 캐릭터********/
	/************************/
	/** 너구리 : 왼쪽*/
	public static String RACCOON_LEFT_SPRITES = DIR_RACCOON + "\\CharterLeftSprite.png";
	/** 너구리 : 오른쪽*/
	public static String RACCOON_RIGHT_SPRITES = DIR_RACCOON + "\\CharterRightSprite.png";
	/** 너구리 : 추락*/
	public static String RACCOON_FALL_SPRITES = DIR_RACCOON + "\\fallSprite.png";
	/** 너구리 : 사다리타기*/
	public static String RACCOON_LADDER_SPRITES = DIR_RACCOON + "\\LadderSprite.png";
	/** 너구리 : 왼쪽 점프 1*/
	public static String RACCOON_LEFT_JUMP1 = DIR_RACCOON + "\\leftJump1.png";
	/** 너구리 : 왼쪽 점프 2*/
	public static String RACCOON_LEFT_JUMP2 = DIR_RACCOON + "\\leftJump2.png";
	/** 너구리 : 오른쪽 점프 1*/
	public static String RACCOON_RIGHT_JUMP1 = DIR_RACCOON + "\\rightJump1.png";
	/** 너구리 : 오른쪽 점프 2*/
	public static String RACCOON_RIGHT_JUMP2 = DIR_RACCOON + "\\rightJump2.png";

	/**********************/
	/********적캐릭터********/
	/**********************/
	/** 적 캐릭터 : 초록 지네 (오른쪽 방향 낮은 꼬리)*/
	public static String ENEMY_RIGHT_GREEN_CENTIPEDE_DOWN = DIR_ENEMY + "\\rightCentipede_green.png";
	/** 적 캐릭터 : 초록 지네 (오른쪽 방향 높은 꼬리)*/
	public static String ENEMY_RIGHT_GREEN_CENTIPEDE_UP = DIR_ENEMY + "\\rightCentipede2_green.png";
	/** 적 캐릭터 : 초록 지네 (왼쪽 방향 낮은 꼬리)*/
	public static String ENEMY_LEFT_GREEN_CENTIPEDE_DOWN = DIR_ENEMY + "\\leftCentipede_green.png";
	/** 적 캐릭터 : 초록 지네 (왼쪽 방향 높은 꼬리)*/
	public static String ENEMY_LEFT_GREEN_CENTIPEDE_UP = DIR_ENEMY + "\\leftCentipede2_green.png";

	/** 적 캐릭터 : 주황 지네 (오른쪽 방향 낮은 꼬리)*/
	public static String ENEMY_RIGHT_ORANGE_CENTIPEDE_DOWN = DIR_ENEMY + "\\rightCentipede_orange.png";
	/** 적 캐릭터 : 주황 지네 (오른쪽 방향 높은 꼬리)*/
	public static String ENEMY_RIGHT_ORANGE_CENTIPEDE_UP = DIR_ENEMY + "\\rightCentipede2_orange.png";
	/** 적 캐릭터 : 주황 지네 (왼쪽 방향 낮은 꼬리)*/
	public static String ENEMY_LEFT_ORANGE_CENTIPEDE_DOWN = DIR_ENEMY + "\\leftCentipede_orange.png";
	/** 적 캐릭터 : 주황 지네 (왼쪽 방향 높은 꼬리)*/
	public static String ENEMY_LEFT_ORANGE_CENTIPEDE_UP = DIR_ENEMY + "\\leftCentipede2_orange.png";

	/** 적 캐릭터 : 빨강 지네 (오른쪽 방향 낮은 꼬리)*/
	public static String ENEMY_RIGHT_RED_CENTIPEDE_DOWN = DIR_ENEMY + "\\rightCentipede_red.png";
	/** 적 캐릭터 : 빨강 지네 (오른쪽 방향 높은 꼬리)*/
	public static String ENEMY_RIGHT_RED_CENTIPEDE_UP = DIR_ENEMY + "\\rightCentipede2_red.png";
	/** 적 캐릭터 : 빨강 지네 (왼쪽 방향 낮은 꼬리)*/
	public static String ENEMY_LEFT_RED_CENTIPEDE_DOWN = DIR_ENEMY + "\\leftCentipede_red.png";
	/** 적 캐릭터 : 빨강 지네 (왼쪽 방향 높은 꼬리)*/
	public static String ENEMY_LEFT_RED_CENTIPEDE_UP = DIR_ENEMY + "\\leftCentipede2_red.png";

	/** 적 캐릭터 : 초록 뱀 (오른쪽 방향 낮은 혀)*/
	public static String ENEMY_RIGHT_GREEN_SNAKE_DOWN = DIR_ENEMY + "\\rightSnake2_green.png";
	/** 적 캐릭터 : 초록 뱀 (오른쪽 방향 높은 혀)*/
	public static String ENEMY_RIGHT_GREEN_SNAKE_UP = DIR_ENEMY + "\\rightSnake_green.png";
	/** 적 캐릭터 : 초록 뱀 (왼쪽 방향 낮은 혀)*/
	public static String ENEMY_LEFT_GREEN_SNAKE_DOWN = DIR_ENEMY + "\\leftSnake2_green.png";
	/** 적 캐릭터 : 초록 뱀 (왼쪽 방향 높은 혀)*/
	public static String ENEMY_LEFT_GREEN_SNAKE_UP = DIR_ENEMY + "\\leftSnake_green.png";

	/** 적 캐릭터 : 주황 뱀 (오른쪽 방향 낮은 혀)*/
	public static String ENEMY_RIGHT_ORANGE_SNAKE_DOWN = DIR_ENEMY + "\\rightSnake2_orange.png";
	/** 적 캐릭터 : 주황 뱀 (오른쪽 방향 높은 혀)*/
	public static String ENEMY_RIGHT_ORANGE_SNAKE_UP = DIR_ENEMY + "\\rightSnake_orange.png";
	/** 적 캐릭터 : 주황 뱀 (왼쪽 방향 낮은 혀)*/
	public static String ENEMY_LEFT_ORANGE_SNAKE_DOWN = DIR_ENEMY + "\\leftSnake2_orange.png";
	/** 적 캐릭터 : 주황 뱀 (왼쪽 방향 높은 혀)*/
	public static String ENEMY_LEFT_ORANGE_SNAKE_UP = DIR_ENEMY + "\\leftSnake_orange.png";

	/** 적 캐릭터 : 빨강 뱀 (오른쪽 방향 낮은 혀)*/
	public static String ENEMY_RIGHT_RED_SNAKE_DOWN = DIR_ENEMY + "\\rightSnake2_red.png";
	/** 적 캐릭터 : 빨강 뱀 (오른쪽 방향 높은 혀)*/
	public static String ENEMY_RIGHT_RED_SNAKE_UP = DIR_ENEMY + "\\rightSnake_red.png";
	/** 적 캐릭터 : 빨강 뱀 (왼쪽 방향 낮은 혀)*/
	public static String ENEMY_LEFT_RED_SNAKE_DOWN = DIR_ENEMY + "\\leftSnake2_red.png";
	/** 적 캐릭터 : 빨강 뱀 (왼쪽 방향 높은 혀)*/
	public static String ENEMY_LEFT_RED_SNAKE_UP = DIR_ENEMY + "\\leftSnake_red.png";

	/**********************/
	/********아이템류********/
	/**********************/
	/** 적 오브젝트 : 압정*/
	public static String ENEMY_GIMLET = DIR_ITEM + "\\gimlet.png";
	/** 오브젝트 : 랜덤 주머니*/
	public static String OBJECT_SCRETITEM = DIR_ITEM + "\\scretItem.png";

	/********************/
	/********음식류********/
	/********************/
	/** 음식 : 사과*/
	public static String FOOD_APPLE = DIR_FOOD + "\\apple.png";
	/** 음식 : 아보카도*/
	public static String FOOD_AVOCADO = DIR_FOOD + "\\avocado.png";
	/** 음식 : 바나나*/
	public static String FOOD_BANANA = DIR_FOOD + "\\banana.png";
	/** 음식 : 맥주*/
	public static String FOOD_BEER = DIR_FOOD + "\\beer.png";
	/** 음식 : 체리*/
	public static String FOOD_CHERRY = DIR_FOOD + "\\cherry.png";
	/** 음식 : 옥수수*/
	public static String FOOD_CORN = DIR_FOOD + "\\corn.png";
	/** 음식 : 파인애플*/
	public static String FOOD_FINEAPPLE = DIR_FOOD + "\\fineapple.png";
	/** 음식 : 레몬*/
	public static String FOOD_LEMON = DIR_FOOD + "\\lemon.png";
	/** 음식 : 오렌지*/
	public static String FOOD_ORANGE = DIR_FOOD + "\\orange.png";
	/** 음식 : 수박*/
	public static String FOOD_WATERMELON = DIR_FOOD + "\\watermelon.png";

	/********************/
	/********스테이지*******/
	/********************/
	/** 스테이지 : 첫번째*/
	public static String STAGE_FIRST = DIR_STAGE + "\\Stage_First.png";
	/** 스테이지 : 두번째*/
	public static String STAGE_SECOND = DIR_STAGE + "\\Stage_Second.png";
	/** 스테이지 : 세번째*/
	public static String STAGE_THIRD = DIR_STAGE + "\\Stage_Third.png";
	/** 스테이지 : 네번째*/
	public static String STAGE_FOURTH = DIR_STAGE + "\\Stage_Fourth.png";
	/** 스테이지 : 다섯번째*/
	public static String STAGE_FIFTH = DIR_STAGE + "\\Stage_Fifth.png";
	/** 스테이지 : 여섯번째*/
	public static String STAGE_SIXTH = DIR_STAGE + "\\Stage_Sixth.png";
	/** 스테이지 : 일곱번째*/
	public static String STAGE_SEVENTH = DIR_STAGE + "\\Stage_Seventh.png";
	/** 스테이지 : 여덟번째*/
	public static String STAGE_EIGHTH = DIR_STAGE + "\\Stage_Eighth.png";
	/** 스테이지 : 아홉번째*/
	public static String STAGE_NINTH = DIR_STAGE + "\\Stage_Ninth.png";
	/** 스테이지 : 열번째*/
	public static String STAGE_TENTH = DIR_STAGE + "\\Stage_Tenth.png";

	/*******************/
	/********배경********/
	/******************/
	/** 배경 : 시작 화면*/
	public static String BACKGROUND_START = DIR_START + "\\BackGround_Start.png";
}

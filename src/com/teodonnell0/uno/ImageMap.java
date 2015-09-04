package com.teodonnell0.uno;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageMap {

	private final static Map<ImageName, Image> imageMap = new HashMap<>();
	
	public enum ImageName {
		Menu_Background,
		Down_Arrow,
		Right_Arrow,
		Left_Arrow,
		Up_Arrow,
		Red_Reverse,
		Red_Zero,
		Red_One,
		Red_Two,
		Red_Three,
		Red_Four,
		Red_Five,
		Red_Six,
		Red_Seven,
		Red_Eight,
		Red_Nine,
		Blue_Reverse,
		Blue_Zero,
		Blue_One,
		Blue_Two,
		Blue_Three,
		Blue_Four,
		Blue_Five,
		Blue_Six,
		Blue_Seven,
		Blue_Eight,
		Blue_Nine,
		Green_Reverse,
		Green_Zero,
		Green_One,
		Green_Two,
		Green_Three,
		Green_Four,
		Green_Five,
		Green_Six,
		Green_Seven,
		Green_Eight,
		Green_Nine,
		Yellow_Reverse,
		Yellow_Zero,
		Yellow_One,
		Yellow_Two,
		Yellow_Three,
		Yellow_Four,
		Yellow_Five,
		Yellow_Six,
		Yellow_Seven,
		Yellow_Eight,
		Yellow_Nine,
		Red_Draw_Two,
		Blue_Draw_Two,
		Green_Draw_Two,
		Yellow_Draw_Two,
		Red_Skip,
		Blue_Skip,
		Green_Skip,
		Yellow_Skip,
		Wild_Draw_Four,
		Wild,
		Game_Board_Red,
		Game_Board_Blue,
		Game_Board_Green,
		Game_Board_Yellow,
		Card_Back,
		Uno_Logo,
	}
	
	static {
		try {			
			imageMap.put(ImageName.Menu_Background, ImageIO.read(new File("./resources/menu_background.png")));
			
			imageMap.put(ImageName.Down_Arrow, ImageIO.read(new File("./resources/down_arrow.png")));
			imageMap.put(ImageName.Right_Arrow, ImageIO.read(new File("./resources/right_arrow.png")));
			imageMap.put(ImageName.Left_Arrow, ImageIO.read(new File("./resources/left_arrow.png")));
			imageMap.put(ImageName.Up_Arrow, ImageIO.read(new File("./resources/up_arrow.png")));
			
			imageMap.put(ImageName.Card_Back, ImageIO.read(new File("./resources/card_back.png")));
			imageMap.put(ImageName.Game_Board_Red,  ImageIO.read(new File("./resources/board_red.png")));
			imageMap.put(ImageName.Game_Board_Blue,  ImageIO.read(new File("./resources/board_blue.png")));
			imageMap.put(ImageName.Game_Board_Green,  ImageIO.read(new File("./resources/board_green.png")));
			imageMap.put(ImageName.Game_Board_Yellow,  ImageIO.read(new File("./resources/board_yellow.png")));
			BufferedImage cardImage = ImageIO.read(new File("./resources/cards.png"));
			
			imageMap.put(ImageName.Red_Reverse, cardImage.getSubimage(5, 5, 92, 135));
			imageMap.put(ImageName.Red_One, cardImage.getSubimage(108, 5, 92, 135));
			imageMap.put(ImageName.Red_Two, cardImage.getSubimage(210, 5, 92, 135));
			imageMap.put(ImageName.Red_Three, cardImage.getSubimage(312, 5, 92, 135));
			imageMap.put(ImageName.Red_Four, cardImage.getSubimage(415, 5, 92, 135));
			imageMap.put(ImageName.Red_Five, cardImage.getSubimage(517, 5, 92, 135));
			imageMap.put(ImageName.Red_Six, cardImage.getSubimage(620, 5, 92, 135));
			imageMap.put(ImageName.Red_Seven, cardImage.getSubimage(722, 5, 92, 135));
			imageMap.put(ImageName.Red_Eight, cardImage.getSubimage(825, 5, 92, 135));
			imageMap.put(ImageName.Red_Nine, cardImage.getSubimage(926, 5, 92, 135));
			imageMap.put(ImageName.Blue_Reverse, cardImage.getSubimage(5, 151, 92, 135));
			imageMap.put(ImageName.Blue_One, cardImage.getSubimage(108, 151, 92, 135));
			imageMap.put(ImageName.Blue_Two, cardImage.getSubimage(210, 151, 92, 135));
			imageMap.put(ImageName.Blue_Three, cardImage.getSubimage(312, 151, 92, 135));
			imageMap.put(ImageName.Blue_Four, cardImage.getSubimage(415, 151, 92, 135));
			imageMap.put(ImageName.Blue_Five, cardImage.getSubimage(517, 151, 92, 135));
			imageMap.put(ImageName.Blue_Six, cardImage.getSubimage(620, 151, 92, 135));
			imageMap.put(ImageName.Blue_Seven, cardImage.getSubimage(722, 151, 92, 135));
			imageMap.put(ImageName.Blue_Eight, cardImage.getSubimage(825, 151, 92, 135));
			imageMap.put(ImageName.Blue_Nine, cardImage.getSubimage(926, 151, 92, 135));
			imageMap.put(ImageName.Green_Reverse, cardImage.getSubimage(5, 297, 92, 135));
			imageMap.put(ImageName.Green_One, cardImage.getSubimage(108, 297, 92, 135));
			imageMap.put(ImageName.Green_Two, cardImage.getSubimage(210, 297, 92, 135));
			imageMap.put(ImageName.Green_Three, cardImage.getSubimage(312, 297, 92, 135));
			imageMap.put(ImageName.Green_Four, cardImage.getSubimage(415, 297, 92, 135));
			imageMap.put(ImageName.Green_Five, cardImage.getSubimage(517, 297, 92, 135));
			imageMap.put(ImageName.Green_Six, cardImage.getSubimage(620, 297, 92, 135));
			imageMap.put(ImageName.Green_Seven, cardImage.getSubimage(722, 297, 92, 135));
			imageMap.put(ImageName.Green_Eight, cardImage.getSubimage(825, 297, 92, 135));
			imageMap.put(ImageName.Green_Nine, cardImage.getSubimage(926, 297, 92, 135));
			imageMap.put(ImageName.Yellow_Reverse, cardImage.getSubimage(5, 444, 92, 135));
			imageMap.put(ImageName.Yellow_One, cardImage.getSubimage(108, 444, 92, 135));
			imageMap.put(ImageName.Yellow_Two, cardImage.getSubimage(210, 444, 92, 135));
			imageMap.put(ImageName.Yellow_Three, cardImage.getSubimage(312, 444, 92, 135));
			imageMap.put(ImageName.Yellow_Four, cardImage.getSubimage(415, 444, 92, 135));
			imageMap.put(ImageName.Yellow_Five, cardImage.getSubimage(517, 444, 92, 135));
			imageMap.put(ImageName.Yellow_Six, cardImage.getSubimage(620, 444, 92, 135));
			imageMap.put(ImageName.Yellow_Seven, cardImage.getSubimage(722, 444, 92, 135));
			imageMap.put(ImageName.Yellow_Eight, cardImage.getSubimage(825, 444, 92, 135));
			imageMap.put(ImageName.Yellow_Nine, cardImage.getSubimage(926, 444, 92, 135));
			imageMap.put(ImageName.Red_Draw_Two, cardImage.getSubimage(6, 590, 92, 135));
			imageMap.put(ImageName.Blue_Draw_Two, cardImage.getSubimage(108, 590, 92, 135));
			imageMap.put(ImageName.Green_Draw_Two, cardImage.getSubimage(210, 590, 92, 135));
			imageMap.put(ImageName.Yellow_Draw_Two, cardImage.getSubimage(312, 590, 92, 135));
			imageMap.put(ImageName.Red_Skip, cardImage.getSubimage(415, 590, 92, 135));
			imageMap.put(ImageName.Blue_Skip, cardImage.getSubimage(517, 590, 92, 135));
			imageMap.put(ImageName.Green_Skip, cardImage.getSubimage(620, 590, 92, 135));
			imageMap.put(ImageName.Yellow_Skip, cardImage.getSubimage(722, 590, 92, 135));
			imageMap.put(ImageName.Wild_Draw_Four, cardImage.getSubimage(825, 590, 92, 135));
			
			imageMap.put(ImageName.Wild, cardImage.getSubimage(6, 736, 92, 135));
			imageMap.put(ImageName.Red_Zero, cardImage.getSubimage(210, 736, 92, 135));
			imageMap.put(ImageName.Blue_Zero, cardImage.getSubimage(312, 736, 92, 135));
			imageMap.put(ImageName.Green_Zero, cardImage.getSubimage(415, 736, 92, 135));
			imageMap.put(ImageName.Yellow_Zero, cardImage.getSubimage(517, 736, 92, 135));

			imageMap.put(ImageName.Uno_Logo, ImageIO.read(new File("./resources/uno_logo.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Image getImage(ImageName imageName) {
		return imageMap.get(imageName);
	}
}

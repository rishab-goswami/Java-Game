package dev.Rishi.tilegame.gfx;

import java.awt.image.BufferedImage;

//creating the assets for all the sprites, tiles, buttons, screens used in the game
public class Assets { 
	private static final int WIDTH = 32, HEIGHT = 32, PLAYERWIDTH = 48, PLAYERHEIGHT = 48, BOSSWIDTH = 64, BOSSHEIGHT = 64;

	public static BufferedImage dark_tile, wood, cracked_wood, left_wall, top_wall, right_wall, bottom_wall, top_left_edge, 
								top_right_edge, bottom_right_edge, bottom_left_edge, bottom_left_edge_2, bottom_right_edge_2, 
								top_right_edge_2, top_left_edge_2, start_menu, computer_desk, bed, gray_closet, brown_closet,
								lamp_stool, stool, seat, seat_2, radio, bench, pot, pot_plant, drawer, drawer_2,green_mat,
								blue_mat, pink_mat, blue_locker, game_over, pause_menu, quit_screen, nothing, checker_tile,
								cracked_checker_tile, background, key, heart, noise, inventory_screen, playerAttackUp,
								playerAttackDown, playerAttackLeft, playerAttackRight, sword, projectile;

	public static BufferedImage[] player_down, player_up, player_left, player_right, enemy_down, enemy_up, enemy_right,
									enemy_left,	button_start, button_coop, button_credits, button_exit, boss_down,
									boss_up, boss_left, boss_right, boss_down_attack, boss_up_attack, boss_left_attack,
									boss_right_attack, button_retry, button_main_menu, button_gameover_exit,
									hearts, noisebar, button_tryAgain, button_resume, button_pauseMainMenu,
									button_yes, button_no, button_high_score, button_back, button_instructions,
									button_hard, button_normal;

	

	public static void init() {
		SpriteSheet Entity_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/16X16.png"));
		SpriteSheet Entity_sheet2 = new SpriteSheet(ImageLoader.loadImage("/textures/LPC_house_interior/interior.png"));
		SpriteSheet player = new SpriteSheet(ImageLoader.loadImage("/textures/george.png"));
		SpriteSheet HeartSheet = new SpriteSheet(ImageLoader.loadImage("/textures/HeartSheet.png"));	
		SpriteSheet NoiseSheet = new SpriteSheet(ImageLoader.loadImage("/textures/NosieBarSheet.png"));
		SpriteSheet boss = new SpriteSheet (ImageLoader.loadImage("/textures/lpc_entry/png/walkcycle/BODY_skeleton.png"));
		SpriteSheet bossAttack = new SpriteSheet (ImageLoader.loadImage("/textures/lpc_entry/png/slash/BODY_skeleton.png"));		
		
		start_menu = ImageLoader.loadImage("/textures/Start Menu.png");
		game_over = ImageLoader.loadImage("/textures/Game Over.png");
		pause_menu = ImageLoader.loadImage("/textures/Pause Menu.png");
		quit_screen = ImageLoader.loadImage("/textures/Exit Screen.png");
		background = ImageLoader.loadImage("/textures/Background.png");
		
		nothing = Entity_sheet.Crop(160, 160, 1, 1);
		
		//setting button assets on start menu
		button_start = new BufferedImage[2];
		button_start[0] = ImageLoader.loadImage("/textures/Button 1.png");
		button_start[1] = ImageLoader.loadImage("/textures/Button 1 (Select).png");
		
		button_coop = new BufferedImage[2];
		button_coop[0] = ImageLoader.loadImage("/textures/Button 2.png");
		button_coop[1] = ImageLoader.loadImage("/textures/Button 2 (Select).png");
		
		button_credits = new BufferedImage[2];
		button_credits[0] = ImageLoader.loadImage("/textures/Button 3.png");
		button_credits[1] = ImageLoader.loadImage("/textures/Button 3 (Select).png");
		
		button_exit = new BufferedImage[2];
		button_exit[0] = ImageLoader.loadImage("/textures/Button 4.png");
		button_exit[1] = ImageLoader.loadImage("/textures/Button 4 (Select).png");
		
		button_high_score = new BufferedImage[2];
		button_high_score[0] = ImageLoader.loadImage("/textures/High Scores Button.png");
		button_high_score[1] = ImageLoader.loadImage("/textures/High Scores Button (Selected).png");
		
		button_back = new BufferedImage[2];
		button_back[0] = ImageLoader.loadImage("/textures/Back.png");
		button_back[1] = ImageLoader.loadImage("/textures/Back (Selected).png");
		
		button_instructions = new BufferedImage[2];
		button_instructions[0] = ImageLoader.loadImage("/textures/How To Play.png");
		button_instructions[1] = ImageLoader.loadImage("/textures/How To Play (Selected).png");
		
		//setting button assets on game over menu
		button_tryAgain = new BufferedImage[2];
		button_tryAgain[0] = ImageLoader.loadImage("/textures/Try Again.png");
		button_tryAgain[1] = ImageLoader.loadImage("/textures/Try Again (Selected).png");
		
		button_main_menu = new BufferedImage[2];
		button_main_menu[0] = ImageLoader.loadImage("/textures/Menu.png");
		button_main_menu[1] = ImageLoader.loadImage("/textures/Menu (Selected).png");
		
		button_gameover_exit = new BufferedImage[2];
		button_gameover_exit[0] = ImageLoader.loadImage("/textures/Game Over Exit.png");
		button_gameover_exit[1] = ImageLoader.loadImage("/textures/Game Over Exit (Selected).png");
		
		//setting pause menu assets
		button_retry = new BufferedImage[2];
		button_retry[0] = ImageLoader.loadImage("/textures/Retry.png");
		button_retry[1] = ImageLoader.loadImage("/textures/Retry (Selected).png");
		
		button_resume = new BufferedImage[2];
		button_resume[0] = ImageLoader.loadImage("/textures/Resume.png");
		button_resume[1] = ImageLoader.loadImage("/textures/Resume (Selected).png");
		
		button_pauseMainMenu = new BufferedImage[2];
		button_pauseMainMenu[0] = ImageLoader.loadImage("/textures/Main Menu Button.png");
		button_pauseMainMenu[1] = ImageLoader.loadImage("/textures/Main Menu Button (Selected).png");
		
		//quit menu assets
		button_yes = new BufferedImage[2];
		button_yes[0] = ImageLoader.loadImage("/textures/Yes.png");
		button_yes[1] = ImageLoader.loadImage("/textures/Yes (Selected).png");
		
		button_no = new BufferedImage[2];
		button_no[0] = ImageLoader.loadImage("/textures/No.png");
		button_no[1] = ImageLoader.loadImage("/textures/No (Selected).png");
		
		//difficulty menu buttons
		button_hard = new BufferedImage[2];
		button_hard[0] = ImageLoader.loadImage("/textures/Hard Mode.png");
		button_hard[1] = ImageLoader.loadImage("/textures/Hard Mode (Selected).png");
		
		button_normal = new BufferedImage[2];
		button_normal[0] = ImageLoader.loadImage("/textures/Normal Mode.png");
		button_normal[1] = ImageLoader.loadImage("/textures/Normal Mode (Selected).png");
		
		hearts = new BufferedImage[11];
		hearts[0] = HeartSheet.Crop(0, 0, WIDTH*5, HEIGHT);
		hearts[1] = HeartSheet.Crop(0, HEIGHT, WIDTH*5, HEIGHT);
		hearts[2] = HeartSheet.Crop(0, HEIGHT*2, WIDTH*5, HEIGHT);
		hearts[3] = HeartSheet.Crop(0, HEIGHT*3, WIDTH*5, HEIGHT);
		hearts[4] = HeartSheet.Crop(0, HEIGHT*4, WIDTH*5, HEIGHT);
		hearts[5] = HeartSheet.Crop(0, HEIGHT*5, WIDTH*5, HEIGHT);
		hearts[6] = HeartSheet.Crop(0, HEIGHT*6, WIDTH*5, HEIGHT);
		hearts[7] = HeartSheet.Crop(0, HEIGHT*7, WIDTH*5, HEIGHT);
		hearts[8] = HeartSheet.Crop(0, HEIGHT*8, WIDTH*5, HEIGHT);
		hearts[9] = HeartSheet.Crop(0, HEIGHT*9, WIDTH*5, HEIGHT);
		hearts[10] = HeartSheet.Crop(0, HEIGHT*10, WIDTH*5, HEIGHT);
		
		noisebar = new BufferedImage[6];
		noisebar[0] = NoiseSheet.Crop(0, 0, WIDTH*5, HEIGHT);
		noisebar[1] = NoiseSheet.Crop(0, HEIGHT, WIDTH*5, HEIGHT);
		noisebar[2] = NoiseSheet.Crop(0, HEIGHT*2, WIDTH*5, HEIGHT);
		noisebar[3] = NoiseSheet.Crop(0, HEIGHT*3, WIDTH*5, HEIGHT);
		noisebar[4] = NoiseSheet.Crop(0, HEIGHT*4, WIDTH*5, HEIGHT);
		noisebar[5] = NoiseSheet.Crop(0, HEIGHT*5, WIDTH*5, HEIGHT);
		
		
		//Creatures
		//index of arrays is the number of frames for each animation
		//Player
		player_down = new BufferedImage[4];
		player_up = new BufferedImage[4];
		player_right = new BufferedImage[4];
		player_left = new BufferedImage[4];
		
		player_down[0] = player.Crop(0, 0, PLAYERWIDTH, PLAYERHEIGHT);
		player_down[1] = player.Crop(0, PLAYERHEIGHT, PLAYERWIDTH, PLAYERHEIGHT);
		player_down[2] = player.Crop(0, PLAYERHEIGHT*2, PLAYERWIDTH, PLAYERHEIGHT);
		player_down[3] = player.Crop(0, PLAYERHEIGHT*3, PLAYERWIDTH, PLAYERHEIGHT);
		
		player_up[0] = player.Crop(PLAYERWIDTH*2, 0, PLAYERWIDTH, PLAYERHEIGHT);
		player_up[1] = player.Crop(PLAYERWIDTH*2, PLAYERHEIGHT, PLAYERWIDTH, PLAYERHEIGHT);
		player_up[2] = player.Crop(PLAYERWIDTH*2, PLAYERHEIGHT*2, PLAYERWIDTH, PLAYERHEIGHT);
		player_up[3] = player.Crop(PLAYERWIDTH*2, PLAYERHEIGHT*3, PLAYERWIDTH, PLAYERHEIGHT);	
		
		player_right[0] = player.Crop(PLAYERWIDTH*3, 0, PLAYERWIDTH, PLAYERHEIGHT);
		player_right[1] = player.Crop(PLAYERWIDTH*3, PLAYERHEIGHT, PLAYERWIDTH, PLAYERHEIGHT);
		player_right[2] = player.Crop(PLAYERWIDTH*3, PLAYERHEIGHT*2, PLAYERWIDTH, PLAYERHEIGHT);
		player_right[3] = player.Crop(PLAYERWIDTH*3, PLAYERHEIGHT*3, PLAYERWIDTH, PLAYERHEIGHT);
		
		player_left[0] = player.Crop(PLAYERWIDTH, 0, PLAYERWIDTH, PLAYERHEIGHT);
		player_left[1] = player.Crop(PLAYERWIDTH, PLAYERHEIGHT, PLAYERWIDTH, PLAYERHEIGHT);
		player_left[2] = player.Crop(PLAYERWIDTH, PLAYERHEIGHT*2, PLAYERWIDTH, PLAYERHEIGHT);
		player_left[3] = player.Crop(PLAYERWIDTH, PLAYERHEIGHT*3, PLAYERWIDTH, PLAYERHEIGHT);
		
		playerAttackUp = ImageLoader.loadImage("/textures/George Attack Up.png");
		playerAttackDown = ImageLoader.loadImage("/textures/George Attack Down.png");
		playerAttackLeft = ImageLoader.loadImage("/textures/George Attack Left.png");
		playerAttackRight = ImageLoader.loadImage("/textures/George Attack Right.png");
		
		//Enemies
		
		enemy_down = new BufferedImage[2]; 
		enemy_up = new BufferedImage[2];
		enemy_left = new BufferedImage[2];
		enemy_right= new BufferedImage[2];
		 
		enemy_down[0] = ImageLoader.loadImage("/textures/skl1_fr1.png");
		enemy_down[1] = ImageLoader.loadImage("/textures/skl1_fr2.png");
		
		enemy_up[0] = ImageLoader.loadImage("/textures/skl1_bk1.png");
		enemy_up[1] = ImageLoader.loadImage("/textures/skl1_bk2.png");	
		
		enemy_left[0] = ImageLoader.loadImage("/textures/skl1_lf1.png");
		enemy_left[1] = ImageLoader.loadImage("/textures/skl1_lf2.png");
		
		enemy_right[0] = ImageLoader.loadImage("/textures/skl1_rt1.png");
		enemy_right[1] = ImageLoader.loadImage("/textures/skl1_rt2.png");
		
		boss_up = new BufferedImage[9];
		boss_down = new BufferedImage[9]; 
		boss_left = new BufferedImage[9];
		boss_right= new BufferedImage[9];
		 
		boss_down[0] = boss.Crop(0, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down[1] = boss.Crop(BOSSWIDTH, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down[2] = boss.Crop(BOSSWIDTH*2, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down[3] = boss.Crop(BOSSWIDTH*3, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down[4] = boss.Crop(BOSSWIDTH*4, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down[5] = boss.Crop(BOSSWIDTH*5, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down[6] = boss.Crop(BOSSWIDTH*6, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down[7] = boss.Crop(BOSSWIDTH*7, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down[8] = boss.Crop(BOSSWIDTH*8, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		
		boss_up[0] = boss.Crop(0, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up[1] = boss.Crop(BOSSWIDTH, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up[2] = boss.Crop(BOSSWIDTH*2, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up[3] = boss.Crop(BOSSWIDTH*3, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up[4] = boss.Crop(BOSSWIDTH*4, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up[5] = boss.Crop(BOSSWIDTH*5, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up[6] = boss.Crop(BOSSWIDTH*6, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up[7] = boss.Crop(BOSSWIDTH*7, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up[8] = boss.Crop(BOSSWIDTH*8, 0, BOSSWIDTH, BOSSHEIGHT);
		
		boss_left[0] = boss.Crop(0, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left[1] = boss.Crop(BOSSWIDTH, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left[2] = boss.Crop(BOSSWIDTH*2, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left[3] = boss.Crop(BOSSWIDTH*3, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left[4] = boss.Crop(BOSSWIDTH*4, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left[5] = boss.Crop(BOSSWIDTH*5, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left[6] = boss.Crop(BOSSWIDTH*6, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left[7] = boss.Crop(BOSSWIDTH*7, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left[8] = boss.Crop(BOSSWIDTH*8, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		 
		boss_right[0] = boss.Crop(0, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right[1] = boss.Crop(BOSSWIDTH, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right[2] = boss.Crop(BOSSWIDTH*2, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right[3] = boss.Crop(BOSSWIDTH*3, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right[4] = boss.Crop(BOSSWIDTH*4, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right[5] = boss.Crop(BOSSWIDTH*5, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right[6] = boss.Crop(BOSSWIDTH*6, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right[7] = boss.Crop(BOSSWIDTH*7, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right[8] = boss.Crop(BOSSWIDTH*8, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		
		boss_up_attack = new BufferedImage[6];
		boss_down_attack = new BufferedImage[6]; 
		boss_left_attack = new BufferedImage[6];
		boss_right_attack = new BufferedImage[6];
		  
		boss_down_attack[0] = bossAttack.Crop(0, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down_attack[1] = bossAttack.Crop(BOSSWIDTH, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down_attack[2] = bossAttack.Crop(BOSSWIDTH*2, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down_attack[3] = bossAttack.Crop(BOSSWIDTH*3, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down_attack[4] = bossAttack.Crop(BOSSWIDTH*4, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_down_attack[5] = bossAttack.Crop(BOSSWIDTH*5, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		
		boss_up_attack[0] = bossAttack.Crop(0, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up_attack[1] = bossAttack.Crop(BOSSWIDTH, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up_attack[2] = bossAttack.Crop(BOSSWIDTH*2, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up_attack[3] = bossAttack.Crop(BOSSWIDTH*3, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up_attack[4] = bossAttack.Crop(BOSSWIDTH*4, 0, BOSSWIDTH, BOSSHEIGHT);
		boss_up_attack[5] = bossAttack.Crop(BOSSWIDTH*5, 0, BOSSWIDTH, BOSSHEIGHT);
			 
		boss_left_attack[0] = bossAttack.Crop(0, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left_attack[1] = bossAttack.Crop(BOSSWIDTH, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left_attack[2] = bossAttack.Crop(BOSSWIDTH*2, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left_attack[3] = bossAttack.Crop(BOSSWIDTH*3, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left_attack[4] = bossAttack.Crop(BOSSWIDTH*4, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
		boss_left_attack[5] = bossAttack.Crop(BOSSWIDTH*5, BOSSHEIGHT, BOSSWIDTH, BOSSHEIGHT);
			 
		boss_right_attack[0] = bossAttack.Crop(0, BOSSHEIGHT*2, BOSSWIDTH, BOSSHEIGHT);
		boss_right_attack[1] = bossAttack.Crop(BOSSWIDTH, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right_attack[2] = bossAttack.Crop(BOSSWIDTH*2, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right_attack[3] = bossAttack.Crop(BOSSWIDTH*3, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right_attack[4] = bossAttack.Crop(BOSSWIDTH*4, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		boss_right_attack[5] = bossAttack.Crop(BOSSWIDTH*5, BOSSHEIGHT*3, BOSSWIDTH, BOSSHEIGHT);
		
		//Objects
		computer_desk = Entity_sheet.Crop(0, 0, 35, 34);
		bed = Entity_sheet.Crop(35, 0, 27, 52);
		gray_closet = Entity_sheet.Crop(62, 0, 48, 50);
		brown_closet = Entity_sheet.Crop(112, 0, 48, 50);
		lamp_stool = Entity_sheet.Crop(0, 34, 15, 35);
		stool = Entity_sheet.Crop(15, 34, 15, 35);
		seat = Entity_sheet.Crop(60, 50, 21, 16);
		seat_2 = Entity_sheet.Crop(60, 66, 21, 16);
		radio = Entity_sheet.Crop(90, 50, 25, 16);
		bench = Entity_sheet.Crop(0, 97, 35, 20);
		pot = Entity_sheet.Crop(0, 117, 17, 33);
		pot_plant = Entity_sheet.Crop(17, 117, 17, 33);
		drawer = Entity_sheet.Crop(0, 64, 16, 32);
		drawer_2 = Entity_sheet.Crop(16, 64, 16, 32);
		green_mat = Entity_sheet.Crop(0, 160, 48, 48);
		blue_mat = Entity_sheet.Crop(48, 160, 48, 48);
		pink_mat = Entity_sheet.Crop(96, 160, 48, 48);
		blue_locker = Entity_sheet.Crop(35, 52, 27, 52);
		projectile = ImageLoader.loadImage("/textures/projectile.png");
		
		wood = Entity_sheet2.Crop(0, 96, WIDTH, HEIGHT);
		cracked_wood = Entity_sheet2.Crop(WIDTH, 96, WIDTH, HEIGHT);
		checker_tile = Entity_sheet2.Crop(0, 96+WIDTH, WIDTH, HEIGHT);
		cracked_checker_tile = Entity_sheet2.Crop(0, 96+WIDTH*2, WIDTH, HEIGHT);
		
		left_wall = Entity_sheet2.Crop(128, 40, WIDTH, HEIGHT);
		top_wall = Entity_sheet2.Crop(100, 64, WIDTH, HEIGHT);
		right_wall = Entity_sheet2.Crop(64, 48, WIDTH, HEIGHT);
		bottom_wall = Entity_sheet2.Crop(90, 0, WIDTH, HEIGHT);
		
		top_left_edge = Entity_sheet2.Crop(32, 64, WIDTH, HEIGHT);
		top_right_edge = Entity_sheet2.Crop(0, 64, WIDTH, HEIGHT);
		bottom_right_edge = Entity_sheet2.Crop(0, 32, WIDTH, HEIGHT);
		bottom_left_edge = Entity_sheet2.Crop(32, 32, WIDTH, HEIGHT);
		
		bottom_left_edge_2 = Entity_sheet2.Crop(64, 64, WIDTH, HEIGHT);
		bottom_right_edge_2 = Entity_sheet2.Crop(128, 64, WIDTH, HEIGHT);
		top_right_edge_2 = Entity_sheet2.Crop(128, 0, WIDTH, HEIGHT);
		top_left_edge_2 = Entity_sheet2.Crop(64, 0, WIDTH, HEIGHT);
		
		dark_tile = Entity_sheet2.Crop(71, 15, WIDTH, HEIGHT);
		
		//Items
		key = ImageLoader.loadImage("/textures/57471834_2245710765651713_7952874230561898496_n.png");
		heart = ImageLoader.loadImage("/textures/57272222_380173272829061_3227549811343360000_n.png");
		noise = ImageLoader.loadImage("/textures/FullNoiseBar.png");
		inventory_screen = ImageLoader.loadImage("/textures/InventorySheet.png");
		sword = ImageLoader.loadImage("/textures/Sword.png");
	}
	
}

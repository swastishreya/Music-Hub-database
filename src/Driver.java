import java.util.Scanner;

public class Driver {

	public static void displayOptions(){
        System.out.println("1: Play song");
        System.out.println("2: Create playlist");
        System.out.println("3: Download current song");
        System.out.println("4: Add song to queue");
		System.out.println("5: Upload a song");
		System.out.println("6: Shuffle play");
        System.out.println("7: Exit");
    }

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); 
		DBHelper db = new DBHelper();
		User user = null;
		
		db.initialize();

		System.out.println("Enter login id:");
      	String login_id = sc.nextLine();
		user = db.login(login_id);
		int login_count = 0;
		
		while(user == null && login_count < 3){
			user = db.login(login_id);
			login_count++;
		}

		if(user != null){
			System.out.println("Login successful...");
			System.out.println("Let's play some songs! :) Get started:");

			displayOptions();
			int option = sc.nextInt();

			while(option != 7){
				if(option == 1){
					db.playSong(user);
				}
				else if(option == 2){
					db.createPlaylist(user);
				}
				else if(option == 3){
					db.downloadCurrentSong(user);
				}
				else if(option == 4){
					db.addSongToQueue(user);
				}
				else if(option == 5){
					db.uploadSong(user);
				}
				else if(option == 6){
					db.shufflePlay(user);
				}
				else if(option == 7){
					db.stop();
				}
				displayOptions();
				option = sc.nextInt();

			}
			if(option == 7){ 
				sc.close();
				db.stop();
			}
		}
		else{
			sc.close();
			db.stop();
		}
   

	}
}

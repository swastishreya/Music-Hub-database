public class User {
	private int  user_id;
	private String login_id;
	private String password;
	private String user_name;
	private boolean is_artist;
	private static int currentUserNum = 0;

	public static int getCurrentUserNum(){
		return currentUserNum;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
		currentUserNum++;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public boolean isIs_artist() {
		return is_artist;
	}
	public void setIs_artist(boolean is_artist) {
		this.is_artist = is_artist;
	}
}


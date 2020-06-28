public class Playlist {
	private int playlist_id;
	private String playlist_name;
	private int p_song_id;
    private int p_user_id;
	private boolean is_downloaded;
	private static int currentId = 0;

	Playlist(){
		DBHelper db = new DBHelper();
		currentId = db.getNumOf("playlist") + 1;
		setPlaylistId(currentId);
	}

	public int getPlaylistId() {
		return playlist_id;
	}
	public void setPlaylistId(int playlist_id) {
		this.playlist_id = playlist_id;
	}
	public String getPlaylist_name() {
		return playlist_name;
	}
	public void setPlaylist_name(String playlist_name) {
		this.playlist_name = playlist_name;
	}
	public int getP_song_id() {
		return p_song_id;
	}
	public void setP_song_id(int p_song_id) {
		this.p_song_id = p_song_id;
	}
	public int getP_user_id() {
		return p_user_id;
	}
	public void setP_user_id(int p_user_id) {
		this.p_user_id = p_user_id;
	}
	public boolean isIs_downloaded() {
		return is_downloaded;
	}
	public void setIs_downloaded(boolean is_downloaded) {
		this.is_downloaded = is_downloaded;
	}
}

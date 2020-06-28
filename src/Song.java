public class Song {
	private int song_id;
    private String song_name;
    private boolean s_artist_id;
    private static int currentId = 0;
    
    Song(){
        DBHelper db = new DBHelper();
		currentId = db.getNumOf("song") + 1;
		setSong_id(currentId);
    }

	public int getSong_id() {
		return song_id;
	}
	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}
	public String getSong_name() {
		return song_name;
	}
	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}
	public boolean isS_artist_id() {
		return s_artist_id;
	}
	public void setS_artist_id(boolean s_artist_id) {
		this.s_artist_id = s_artist_id;
	}
}

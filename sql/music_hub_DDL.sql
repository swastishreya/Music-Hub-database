create database music_hub_db;

use music_hub_db;

create table user(
    user_id int NOT NULL,
    login_id varchar(30),
    password varchar(30),
    user_name varchar(30),
    is_artist boolean,
    constraint pk_user PRIMARY KEY (user_id)
);

create table queue(
    queue_id int NOT NULL,
    queue_list varchar(1024),
    is_shuffle_play boolean,
    constraint pk_queue PRIMARY KEY (queue_id)
);

create table playlist(
    playlist_id int NOT NULL,
    playlist_name varchar(30),
    is_downloaded boolean,
    p_user_id int,
    p_song_id int,
    constraint pk_playlist PRIMARY KEY (playlist_id)
);

create table song(
    song_id int NOT NULL,
    song_name varchar(30),
    s_artist_id int,
    constraint pk_song PRIMARY KEY (song_id)
);

alter table queue
    add constraint fk_user_queue FOREIGN KEY (queue_id) REFERENCES user(user_id);

alter table playlist
    add constraint fk_user_playlist FOREIGN KEY (p_user_id) REFERENCES user(user_id),
    add constraint fk_song_playlist FOREIGN KEY (p_song_id) REFERENCES song(song_id);

alter table song
    add constraint fk_artist_song FOREIGN KEY (s_artist_id) REFERENCES user(user_id);

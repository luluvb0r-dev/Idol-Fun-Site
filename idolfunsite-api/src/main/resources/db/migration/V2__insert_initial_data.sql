-- V2__insert_initial_data.sql

-- 1. Site
INSERT INTO site (id, site_key, site_name, idol_name, visibility, status, is_seeded_site) 
VALUES (1, 'equal-love', '=LOVE Fan Site', '=LOVE', 'PUBLIC', 'PUBLISHED', TRUE);

-- 2. Theme Setting
INSERT INTO site_theme_setting (site_id, primary_color_hex, secondary_color_hex, accent_color_hex)
VALUES (1, '#F06292', '#F48FB1', '#FF4081');

-- 3. Group
INSERT INTO idol_group (id, site_id, group_name, group_kana, official_name, display_order)
VALUES (1, 1, '=LOVE', 'いこーるらぶ', '=LOVE', 1);

-- 4. Call Type Master
INSERT INTO call_type_master (site_id, call_type_code, call_type_label, color_hex, icon_key, description, display_order) VALUES
(1, 'CHANT', '掛け声', '#F06292', 'mic', 'メンバー名や「はい！」などのコール', 1),
(1, 'CLAP', 'クラップ', '#FFCA28', 'front_hand', '手拍子', 2),
(1, 'MIX', 'MIX', '#4FC3F7', 'graphic_eq', '「タイガー、ファイヤー...」など', 3),
(1, 'JUMP', 'ジャンプ', '#81C784', 'arrow_upward', '一緒にジャンプする', 4),
(1, 'OTHER', 'その他', '#B0BEC5', 'more_horiz', 'その他の特殊なコール', 5);

-- 5. Member (Sample: =LOVE Iori Noguchi and Maika Sasaki)
INSERT INTO member (id, site_id, member_name, member_name_kana, birthday, member_color, member_color_hex, birthplace, short_bio, display_order) VALUES
(1, 1, '野口 衣織', 'のぐち いおり', '2000-04-26', '紫', '#9C27B0', '茨城県', 'いおりん', 1),
(2, 1, '佐々木 舞香', 'ささき まいか', '2000-01-21', '白', '#FFFFFF', '愛知県', 'まいか', 2);

-- 6. Member Group History
INSERT INTO member_group_history (site_id, member_id, group_id, role_type, generation_label, joined_on, display_order) VALUES
(1, 1, 1, 'MEMBER', '1期生', '2017-09-06', 1),
(1, 2, 1, 'MEMBER', '1期生', '2017-09-06', 2);

-- 7. Single Release
INSERT INTO single_release (id, site_id, release_type, title, title_kana, release_date, display_order) VALUES
(1, 1, 'SINGLE', '=LOVE', 'いこーるらぶ', '2017-09-06', 1),
(2, 1, 'SINGLE', 'あの子コンプレックス', 'あのここんぷれっくす', '2022-05-25', 11);

-- 8. Song
INSERT INTO song (id, site_id, group_id, title, title_kana, has_call_data, display_order) VALUES
(1, 1, 1, '=LOVE', 'いこーるらぶ', TRUE, 1),
(2, 1, 1, 'あの子コンプレックス', 'あのここんぷれっくす', FALSE, 2);

-- 9. Release Song
INSERT INTO release_song (site_id, release_id, song_id, track_number, is_primary, is_title_track, display_order) VALUES
(1, 1, 1, 1, TRUE, TRUE, 1),
(1, 2, 2, 1, TRUE, TRUE, 2);

-- 10. Song Member (Sample vocals)
INSERT INTO song_member (song_id, member_id, role_type) VALUES
(1, 1, 'ORIGINAL_VOCAL'),
(1, 2, 'ORIGINAL_VOCAL'),
(2, 1, 'ORIGINAL_VOCAL'),
(2, 2, 'ORIGINAL_VOCAL');

-- 11. Song Call (Sample for =LOVE)
INSERT INTO song_call_block (id, song_id, block_type, block_label, order_no) VALUES
(1, 1, 'VERSE', '1番Aメロ', 1);

INSERT INTO song_call_line (id, block_id, line_no, lyrics) VALUES
(1, 1, 1, '君に会えたその日から');

INSERT INTO song_call_item (call_line_id, call_type_code, call_text, order_no) VALUES
(1, 'CHANT', 'はい！はい！', 1);

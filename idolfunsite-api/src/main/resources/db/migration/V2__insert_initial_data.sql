-- V2__insert_initial_data.sql

-- 1. Site
INSERT INTO site (site_key, site_name, idol_name, visibility, status, is_seeded_site)
VALUES ('equal-love', '=LOVE Fan Site', '=LOVE', 'PUBLIC', 'PUBLISHED', TRUE);

-- 2. Theme Setting
INSERT INTO site_theme_setting (site_id, primary_color_hex, secondary_color_hex, accent_color_hex)
SELECT id, '#F06292', '#F48FB1', '#FF4081'
FROM site
WHERE site_key = 'equal-love';

-- 3. Group
INSERT INTO idol_group (site_id, group_name, group_kana, official_name, display_order)
SELECT id, '=LOVE', 'equal-love', '=LOVE', 1
FROM site
WHERE site_key = 'equal-love';

-- 4. Call Type Master
INSERT INTO call_type_master (site_id, call_type_code, call_type_label, color_hex, icon_key, description, display_order)
SELECT id, 'CHANT', '掛け声', '#F06292', 'mic', 'Audience response call', 1
FROM site
WHERE site_key = 'equal-love';

INSERT INTO call_type_master (site_id, call_type_code, call_type_label, color_hex, icon_key, description, display_order)
SELECT id, 'CLAP', 'クラップ', '#FFCA28', 'hands', 'Clap along with the song', 2
FROM site
WHERE site_key = 'equal-love';

INSERT INTO call_type_master (site_id, call_type_code, call_type_label, color_hex, icon_key, description, display_order)
SELECT id, 'MIX', 'MIX', '#4FC3F7', 'graphic_eq', 'Fixed chant pattern', 3
FROM site
WHERE site_key = 'equal-love';

INSERT INTO call_type_master (site_id, call_type_code, call_type_label, color_hex, icon_key, description, display_order)
SELECT id, 'JUMP', 'Jump', '#81C784', 'arrow_upward', 'Jump with the song', 4
FROM site
WHERE site_key = 'equal-love';

INSERT INTO call_type_master (site_id, call_type_code, call_type_label, color_hex, icon_key, description, display_order)
SELECT id, 'OTHER', 'Other', '#B0BEC5', 'more_horiz', 'Other support action', 5
FROM site
WHERE site_key = 'equal-love';

-- 5. Member (Sample: Iori Noguchi and Maika Sasaki)
INSERT INTO member (
    site_id,
    member_name,
    member_name_kana,
    birthday,
    member_color,
    member_color_hex,
    birthplace,
    short_bio,
    display_order
)
SELECT id, 'Iori Noguchi', 'noguchi iori', DATE '2000-04-26', 'Purple', '#9C27B0', 'Ibaraki', '=LOVE member', 1
FROM site
WHERE site_key = 'equal-love';

INSERT INTO member (
    site_id,
    member_name,
    member_name_kana,
    birthday,
    member_color,
    member_color_hex,
    birthplace,
    short_bio,
    display_order
)
SELECT id, 'Maika Sasaki', 'sasaki maika', DATE '2000-01-21', 'White', '#FFFFFF', 'Aichi', '=LOVE member', 2
FROM site
WHERE site_key = 'equal-love';

-- 6. Member Group History
INSERT INTO member_group_history (site_id, member_id, group_id, role_type, generation_label, joined_on, display_order)
SELECT s.id, m.id, g.id, 'MEMBER', '1st', DATE '2017-09-06', 1
FROM site s
JOIN idol_group g ON g.site_id = s.id AND g.group_name = '=LOVE'
JOIN member m ON m.site_id = s.id AND m.member_name = 'Iori Noguchi'
WHERE s.site_key = 'equal-love';

INSERT INTO member_group_history (site_id, member_id, group_id, role_type, generation_label, joined_on, display_order)
SELECT s.id, m.id, g.id, 'MEMBER', '1st', DATE '2017-09-06', 2
FROM site s
JOIN idol_group g ON g.site_id = s.id AND g.group_name = '=LOVE'
JOIN member m ON m.site_id = s.id AND m.member_name = 'Maika Sasaki'
WHERE s.site_key = 'equal-love';

-- 7. Single Release
INSERT INTO single_release (site_id, release_type, title, title_kana, release_date, display_order)
SELECT id, 'SINGLE', '=LOVE', 'equal-love', DATE '2017-09-06', 1
FROM site
WHERE site_key = 'equal-love';

INSERT INTO single_release (site_id, release_type, title, title_kana, release_date, display_order)
SELECT id, 'SINGLE', 'Ano Ko Complex', 'anoko complex', DATE '2022-05-25', 11
FROM site
WHERE site_key = 'equal-love';

-- 8. Song
INSERT INTO song (site_id, group_id, title, title_kana, has_call_data, display_order)
SELECT s.id, g.id, '=LOVE', 'equal-love', TRUE, 1
FROM site s
JOIN idol_group g ON g.site_id = s.id AND g.group_name = '=LOVE'
WHERE s.site_key = 'equal-love';

INSERT INTO song (site_id, group_id, title, title_kana, has_call_data, display_order)
SELECT s.id, g.id, 'Ano Ko Complex', 'anoko complex', FALSE, 2
FROM site s
JOIN idol_group g ON g.site_id = s.id AND g.group_name = '=LOVE'
WHERE s.site_key = 'equal-love';

-- 9. Release Song
INSERT INTO release_song (site_id, release_id, song_id, track_number, is_primary, is_title_track, display_order)
SELECT s.id, r.id, so.id, 1, TRUE, TRUE, 1
FROM site s
JOIN single_release r ON r.site_id = s.id AND r.title = '=LOVE'
JOIN song so ON so.site_id = s.id AND so.title = '=LOVE'
WHERE s.site_key = 'equal-love';

INSERT INTO release_song (site_id, release_id, song_id, track_number, is_primary, is_title_track, display_order)
SELECT s.id, r.id, so.id, 1, TRUE, TRUE, 2
FROM site s
JOIN single_release r ON r.site_id = s.id AND r.title = 'Ano Ko Complex'
JOIN song so ON so.site_id = s.id AND so.title = 'Ano Ko Complex'
WHERE s.site_key = 'equal-love';

-- 10. Song Member (Sample vocals)
INSERT INTO song_member (song_id, member_id, role_type)
SELECT so.id, m.id, 'ORIGINAL_VOCAL'
FROM site s
JOIN song so ON so.site_id = s.id AND so.title = '=LOVE'
JOIN member m ON m.site_id = s.id AND m.member_name = 'Iori Noguchi'
WHERE s.site_key = 'equal-love';

INSERT INTO song_member (song_id, member_id, role_type)
SELECT so.id, m.id, 'ORIGINAL_VOCAL'
FROM site s
JOIN song so ON so.site_id = s.id AND so.title = '=LOVE'
JOIN member m ON m.site_id = s.id AND m.member_name = 'Maika Sasaki'
WHERE s.site_key = 'equal-love';

INSERT INTO song_member (song_id, member_id, role_type)
SELECT so.id, m.id, 'ORIGINAL_VOCAL'
FROM site s
JOIN song so ON so.site_id = s.id AND so.title = 'Ano Ko Complex'
JOIN member m ON m.site_id = s.id AND m.member_name = 'Iori Noguchi'
WHERE s.site_key = 'equal-love';

INSERT INTO song_member (song_id, member_id, role_type)
SELECT so.id, m.id, 'ORIGINAL_VOCAL'
FROM site s
JOIN song so ON so.site_id = s.id AND so.title = 'Ano Ko Complex'
JOIN member m ON m.site_id = s.id AND m.member_name = 'Maika Sasaki'
WHERE s.site_key = 'equal-love';

-- 11. Song Call (Sample for =LOVE)
INSERT INTO song_call_block (song_id, block_type, block_label, order_no)
SELECT so.id, 'VERSE', 'Verse 1A', 1
FROM site s
JOIN song so ON so.site_id = s.id AND so.title = '=LOVE'
WHERE s.site_key = 'equal-love';

INSERT INTO song_call_line (block_id, line_no, lyrics)
SELECT scb.id, 1, 'Kimi ni aeta sono hi kara'
FROM site s
JOIN song so ON so.site_id = s.id AND so.title = '=LOVE'
JOIN song_call_block scb ON scb.song_id = so.id AND scb.block_label = 'Verse 1A' AND scb.order_no = 1
WHERE s.site_key = 'equal-love';

INSERT INTO song_call_item (call_line_id, call_type_code, call_text, order_no)
SELECT scl.id, 'CHANT', 'Hai!', 1
FROM site s
JOIN song so ON so.site_id = s.id AND so.title = '=LOVE'
JOIN song_call_block scb ON scb.song_id = so.id AND scb.block_label = 'Verse 1A' AND scb.order_no = 1
JOIN song_call_line scl ON scl.block_id = scb.id AND scl.line_no = 1
WHERE s.site_key = 'equal-love';

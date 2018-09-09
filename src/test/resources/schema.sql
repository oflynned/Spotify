CREATE TABLE IF NOT EXISTS songs
(
  id        SERIAL,
  song_id   BIGINT NOT NULL,
  timestamp BIGINT NOT NULL,
  title     TEXT   COLLATE utf8mb4_unicode_ci NOT NULL,
  CONSTRAINT pk_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS soundcloud_syncs
(
  id        SERIAL,
  timestamp BIGINT NOT NULL,
  success   INT    NOT NULL,
  CONSTRAINT pk_id PRIMARY KEY (id)
);
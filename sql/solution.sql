CREATE DATABASE wallethub;

use wallethub;

CREATE TABLE IF NOT EXISTS log (
  `id` SERIAL UNIQUE NOT NULL PRIMARY KEY,
  `ip` VARCHAR(15) NULL,
  `date` TIMESTAMP NOT NULL,
  `operation` VARCHAR(50) NULL,
  `status` INTEGER NULL,
  `agent` VARCHAR(500) NULL
);

CREATE TABLE IF NOT EXISTS log_comment (
  `id` SERIAL UNIQUE NOT NULL PRIMARY KEY,
  `ip` VARCHAR(15) NULL,
  `date` TIMESTAMP NOT NULL,
  `comment` VARCHAR(200) NULL
);

SELECT ip FROM log
WHERE date BETWEEN :start_date and :end_date
GROUP BY ip
HAVING COUNT(ip) > :threshold;

SELECT * FROM log
WHERE date BETWEEN :start_date and :end_date
AND ip IN (:ip);

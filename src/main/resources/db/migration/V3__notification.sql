ALTER TABLE t_notification
    DROP COLUMN created_at;

ALTER TABLE t_notification
    ADD created_at datetime NULL;

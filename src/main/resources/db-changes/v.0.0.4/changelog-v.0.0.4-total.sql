--liquibase formatted sql

-- changeset akonrusev:1
CREATE SEQUENCE IF NOT EXISTS public.tag_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY tag.id;

ALTER SEQUENCE public.tag_id_seq
    OWNER TO postgres;

CREATE SEQUENCE IF NOT EXISTS public.topic_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY topic.id;

ALTER SEQUENCE public.topic_id_seq
    OWNER TO postgres;

CREATE SEQUENCE IF NOT EXISTS public.note_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY note.id;

ALTER SEQUENCE public.note_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.tag
(
    id integer NOT NULL DEFAULT nextval('tag_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tag_pkey PRIMARY KEY (id),
    CONSTRAINT tag_name_uk UNIQUE (name)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tag
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.topic
(
    id integer NOT NULL DEFAULT nextval('topic_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    parent_id integer,
    CONSTRAINT topic_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.topic
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.note
(
    id integer NOT NULL DEFAULT nextval('note_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    topic_id integer NOT NULL,
    content character varying COLLATE pg_catalog."default",
    CONSTRAINT note_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.note
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.note_to_tag
(
    note_id integer NOT NULL,
    tag_id integer NOT NULL,
    CONSTRAINT note_to_tag_pk PRIMARY KEY (note_id, tag_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.note_to_tag
    OWNER to postgres;
--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: comments; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE comments (
    id integer NOT NULL,
    comment character varying,
    godzillaid integer,
    mediaid integer
);


ALTER TABLE comments OWNER TO "Guest";

--
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comments_id_seq OWNER TO "Guest";

--
-- Name: comments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE comments_id_seq OWNED BY comments.id;


--
-- Name: godzillas; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE godzillas (
    id integer NOT NULL,
    era character varying,
    traits character varying
);


ALTER TABLE godzillas OWNER TO "Guest";

--
-- Name: godzillas_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE godzillas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE godzillas_id_seq OWNER TO "Guest";

--
-- Name: godzillas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE godzillas_id_seq OWNED BY godzillas.id;


--
-- Name: medias; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE medias (
    id integer NOT NULL,
    type character varying,
    description character varying,
    godzillaid integer,
    title character varying
);


ALTER TABLE medias OWNER TO "Guest";

--
-- Name: medias_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE medias_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE medias_id_seq OWNER TO "Guest";

--
-- Name: medias_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE medias_id_seq OWNED BY medias.id;


--
-- Name: ratings; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE ratings (
    id integer NOT NULL,
    ratingnumber integer,
    mediaid integer,
    godzillaid integer
);


ALTER TABLE ratings OWNER TO "Guest";

--
-- Name: ratings_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE ratings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ratings_id_seq OWNER TO "Guest";

--
-- Name: ratings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE ratings_id_seq OWNED BY ratings.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY comments ALTER COLUMN id SET DEFAULT nextval('comments_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY godzillas ALTER COLUMN id SET DEFAULT nextval('godzillas_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY medias ALTER COLUMN id SET DEFAULT nextval('medias_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ratings ALTER COLUMN id SET DEFAULT nextval('ratings_id_seq'::regclass);


--
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY comments (id, comment, godzillaid, mediaid) FROM stdin;
\.


--
-- Name: comments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('comments_id_seq', 3, true);


--
-- Data for Name: godzillas; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY godzillas (id, era, traits) FROM stdin;
\.


--
-- Name: godzillas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('godzillas_id_seq', 6, true);


--
-- Data for Name: medias; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY medias (id, type, description, godzillaid, title) FROM stdin;
\.


--
-- Name: medias_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('medias_id_seq', 4, true);


--
-- Data for Name: ratings; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY ratings (id, ratingnumber, mediaid, godzillaid) FROM stdin;
\.


--
-- Name: ratings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('ratings_id_seq', 199, true);


--
-- Name: comments_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- Name: godzillas_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY godzillas
    ADD CONSTRAINT godzillas_pkey PRIMARY KEY (id);


--
-- Name: medias_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY medias
    ADD CONSTRAINT medias_pkey PRIMARY KEY (id);


--
-- Name: ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY ratings
    ADD CONSTRAINT ratings_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


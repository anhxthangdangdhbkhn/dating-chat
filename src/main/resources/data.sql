PGDMP         #                {            dating-chat    12.13    15.0 Y               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    25895    dating-chat    DATABASE     y   CREATE DATABASE "dating-chat" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';
    DROP DATABASE "dating-chat";
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false                       0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    6            �            1259    26286    follower    TABLE     �   CREATE TABLE public.follower (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    seen boolean NOT NULL,
    followed_id bigint,
    follower_id bigint
);
    DROP TABLE public.follower;
       public         heap    postgres    false    6            �            1259    26284    follower_id_seq    SEQUENCE     x   CREATE SEQUENCE public.follower_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.follower_id_seq;
       public          postgres    false    6    211                       0    0    follower_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.follower_id_seq OWNED BY public.follower.id;
          public          postgres    false    210            �            1259    27186    group    TABLE     <  CREATE TABLE public."group" (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    name character varying(255) NOT NULL,
    random character varying(255),
    type character varying(255),
    url character varying(255),
    admin_id bigint NOT NULL
);
    DROP TABLE public."group";
       public         heap    postgres    false    6            �            1259    27184    group_id_seq    SEQUENCE     u   CREATE SEQUENCE public.group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.group_id_seq;
       public          postgres    false    215    6                       0    0    group_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.group_id_seq OWNED BY public."group".id;
          public          postgres    false    214            �            1259    27197    group_member    TABLE     �   CREATE TABLE public.group_member (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    type character varying(255),
    group_id bigint NOT NULL,
    user_id bigint NOT NULL
);
     DROP TABLE public.group_member;
       public         heap    postgres    false    6            �            1259    27195    group_member_id_seq    SEQUENCE     |   CREATE SEQUENCE public.group_member_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.group_member_id_seq;
       public          postgres    false    6    217                        0    0    group_member_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.group_member_id_seq OWNED BY public.group_member.id;
          public          postgres    false    216            �            1259    26197    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public          postgres    false    6            �            1259    27205    message    TABLE     �   CREATE TABLE public.message (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    content character varying(255),
    delete boolean NOT NULL,
    group_id bigint,
    sender_id bigint
);
    DROP TABLE public.message;
       public         heap    postgres    false    6            �            1259    27203    message_id_seq    SEQUENCE     w   CREATE SEQUENCE public.message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.message_id_seq;
       public          postgres    false    6    219            !           0    0    message_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.message_id_seq OWNED BY public.message.id;
          public          postgres    false    218            �            1259    25896    profile    TABLE       CREATE TABLE public.profile (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    bio character varying(255) NOT NULL,
    location character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public.profile;
       public         heap    postgres    false    6            �            1259    25902    profile_id_seq    SEQUENCE     w   CREATE SEQUENCE public.profile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.profile_id_seq;
       public          postgres    false    202    6            "           0    0    profile_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.profile_id_seq OWNED BY public.profile.id;
          public          postgres    false    203            �            1259    25904    roles    TABLE     V   CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(60)
);
    DROP TABLE public.roles;
       public         heap    postgres    false    6            �            1259    25907    roles_id_seq    SEQUENCE     u   CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.roles_id_seq;
       public          postgres    false    6    204            #           0    0    roles_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
          public          postgres    false    205            �            1259    27085    token    TABLE       CREATE TABLE public.token (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    access_expiry timestamp without time zone,
    access_token character varying(255),
    device_id character varying(255),
    deviceos character varying(255),
    device_type character varying(255),
    ip character varying(255),
    location character varying(255),
    refresh_expiry timestamp without time zone,
    refresh_token character varying(255),
    user_id bigint
);
    DROP TABLE public.token;
       public         heap    postgres    false    6            �            1259    27083    token_id_seq    SEQUENCE     u   CREATE SEQUENCE public.token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.token_id_seq;
       public          postgres    false    6    213            $           0    0    token_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.token_id_seq OWNED BY public.token.id;
          public          postgres    false    212            �            1259    27248    user_receive    TABLE       CREATE TABLE public.user_receive (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    delete boolean NOT NULL,
    read_at timestamp without time zone,
    receive_chat_id bigint,
    receive_user_id bigint
);
     DROP TABLE public.user_receive;
       public         heap    postgres    false    6            �            1259    27246    user_receive_id_seq    SEQUENCE     |   CREATE SEQUENCE public.user_receive_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.user_receive_id_seq;
       public          postgres    false    6    221            %           0    0    user_receive_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.user_receive_id_seq OWNED BY public.user_receive.id;
          public          postgres    false    220            �            1259    25909 
   user_roles    TABLE     ]   CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false    6            �            1259    25912    users    TABLE     �  CREATE TABLE public.users (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    create_expiry timestamp without time zone,
    create_token character varying(255),
    email character varying(40),
    is_account_non_expired boolean NOT NULL,
    is_account_non_locked boolean NOT NULL,
    is_credentials_non_expired boolean NOT NULL,
    is_enabled boolean NOT NULL,
    password character varying(100),
    reset_expiry timestamp without time zone,
    reset_token character varying(255),
    username character varying(255),
    profile_id bigint,
    avatar character varying(255)
);
    DROP TABLE public.users;
       public         heap    postgres    false    6            �            1259    25918    users_id_seq    SEQUENCE     u   CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    6    207            &           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    208            Z           2604    26289    follower id    DEFAULT     j   ALTER TABLE ONLY public.follower ALTER COLUMN id SET DEFAULT nextval('public.follower_id_seq'::regclass);
 :   ALTER TABLE public.follower ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    211    211            \           2604    27189    group id    DEFAULT     f   ALTER TABLE ONLY public."group" ALTER COLUMN id SET DEFAULT nextval('public.group_id_seq'::regclass);
 9   ALTER TABLE public."group" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215            ]           2604    27200    group_member id    DEFAULT     r   ALTER TABLE ONLY public.group_member ALTER COLUMN id SET DEFAULT nextval('public.group_member_id_seq'::regclass);
 >   ALTER TABLE public.group_member ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216    217            ^           2604    27208 
   message id    DEFAULT     h   ALTER TABLE ONLY public.message ALTER COLUMN id SET DEFAULT nextval('public.message_id_seq'::regclass);
 9   ALTER TABLE public.message ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    219    219            W           2604    25920 
   profile id    DEFAULT     h   ALTER TABLE ONLY public.profile ALTER COLUMN id SET DEFAULT nextval('public.profile_id_seq'::regclass);
 9   ALTER TABLE public.profile ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    203    202            X           2604    25921    roles id    DEFAULT     d   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    204            [           2604    27088    token id    DEFAULT     d   ALTER TABLE ONLY public.token ALTER COLUMN id SET DEFAULT nextval('public.token_id_seq'::regclass);
 7   ALTER TABLE public.token ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    212    213    213            _           2604    27251    user_receive id    DEFAULT     r   ALTER TABLE ONLY public.user_receive ALTER COLUMN id SET DEFAULT nextval('public.user_receive_id_seq'::regclass);
 >   ALTER TABLE public.user_receive ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    221    221            Y           2604    25922    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    208    207                      0    26286    follower 
   TABLE DATA           ^   COPY public.follower (id, created_at, updated_at, seen, followed_id, follower_id) FROM stdin;
    public          postgres    false    211   �j                 0    27186    group 
   TABLE DATA           `   COPY public."group" (id, created_at, updated_at, name, random, type, url, admin_id) FROM stdin;
    public          postgres    false    215   k                 0    27197    group_member 
   TABLE DATA           [   COPY public.group_member (id, created_at, updated_at, type, group_id, user_id) FROM stdin;
    public          postgres    false    217   �k                 0    27205    message 
   TABLE DATA           c   COPY public.message (id, created_at, updated_at, content, delete, group_id, sender_id) FROM stdin;
    public          postgres    false    219   �l                 0    25896    profile 
   TABLE DATA           R   COPY public.profile (id, created_at, updated_at, bio, location, name) FROM stdin;
    public          postgres    false    202   +w                 0    25904    roles 
   TABLE DATA           )   COPY public.roles (id, name) FROM stdin;
    public          postgres    false    204   Hw                 0    27085    token 
   TABLE DATA           �   COPY public.token (id, created_at, updated_at, access_expiry, access_token, device_id, deviceos, device_type, ip, location, refresh_expiry, refresh_token, user_id) FROM stdin;
    public          postgres    false    213   �w                 0    27248    user_receive 
   TABLE DATA           u   COPY public.user_receive (id, created_at, updated_at, delete, read_at, receive_chat_id, receive_user_id) FROM stdin;
    public          postgres    false    221   ϓ                 0    25909 
   user_roles 
   TABLE DATA           6   COPY public.user_roles (user_id, role_id) FROM stdin;
    public          postgres    false    206   �                 0    25912    users 
   TABLE DATA           �   COPY public.users (id, created_at, updated_at, create_expiry, create_token, email, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, password, reset_expiry, reset_token, username, profile_id, avatar) FROM stdin;
    public          postgres    false    207   �       '           0    0    follower_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.follower_id_seq', 1, false);
          public          postgres    false    210            (           0    0    group_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.group_id_seq', 6, true);
          public          postgres    false    214            )           0    0    group_member_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.group_member_id_seq', 14, true);
          public          postgres    false    216            *           0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 23, true);
          public          postgres    false    209            +           0    0    message_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.message_id_seq', 132, true);
          public          postgres    false    218            ,           0    0    profile_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.profile_id_seq', 1, false);
          public          postgres    false    203            -           0    0    roles_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.roles_id_seq', 1, false);
          public          postgres    false    205            .           0    0    token_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.token_id_seq', 38, true);
          public          postgres    false    212            /           0    0    user_receive_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.user_receive_id_seq', 184, true);
          public          postgres    false    220            0           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 8, true);
          public          postgres    false    208            i           2606    25924 !   users UK6j5t70rd2eub907qysjvvd76n 
   CONSTRAINT     _   ALTER TABLE ONLY public.users
    ADD CONSTRAINT "UK6j5t70rd2eub907qysjvvd76n" UNIQUE (email);
 M   ALTER TABLE ONLY public.users DROP CONSTRAINT "UK6j5t70rd2eub907qysjvvd76n";
       public            postgres    false    207            m           2606    26291    follower follower_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.follower
    ADD CONSTRAINT follower_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.follower DROP CONSTRAINT follower_pkey;
       public            postgres    false    211            s           2606    27202    group_member group_member_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.group_member
    ADD CONSTRAINT group_member_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.group_member DROP CONSTRAINT group_member_pkey;
       public            postgres    false    217            q           2606    27194    group group_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public."group"
    ADD CONSTRAINT group_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public."group" DROP CONSTRAINT group_pkey;
       public            postgres    false    215            u           2606    27210    message message_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.message DROP CONSTRAINT message_pkey;
       public            postgres    false    219            a           2606    25926    profile profile_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.profile
    ADD CONSTRAINT profile_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.profile DROP CONSTRAINT profile_pkey;
       public            postgres    false    202            c           2606    25928    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public            postgres    false    204            o           2606    27093    token token_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.token DROP CONSTRAINT token_pkey;
       public            postgres    false    213            e           2606    25930 "   roles uk_nb4h0p6txrmfc0xbrd1kglp9t 
   CONSTRAINT     ]   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT uk_nb4h0p6txrmfc0xbrd1kglp9t UNIQUE (name);
 L   ALTER TABLE ONLY public.roles DROP CONSTRAINT uk_nb4h0p6txrmfc0xbrd1kglp9t;
       public            postgres    false    204            w           2606    27253    user_receive user_receive_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.user_receive
    ADD CONSTRAINT user_receive_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.user_receive DROP CONSTRAINT user_receive_pkey;
       public            postgres    false    221            g           2606    25932    user_roles user_roles_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public            postgres    false    206    206            k           2606    25934    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    207            }           2606    27094 !   token FK1jom0f5gewokulym4gc24wrdd    FK CONSTRAINT     �   ALTER TABLE ONLY public.token
    ADD CONSTRAINT "FK1jom0f5gewokulym4gc24wrdd" FOREIGN KEY (user_id) REFERENCES public.users(id);
 M   ALTER TABLE ONLY public.token DROP CONSTRAINT "FK1jom0f5gewokulym4gc24wrdd";
       public          postgres    false    3691    213    207            �           2606    27226 #   message FK54qvll1kht7jgrly9xxuvwbvf    FK CONSTRAINT     �   ALTER TABLE ONLY public.message
    ADD CONSTRAINT "FK54qvll1kht7jgrly9xxuvwbvf" FOREIGN KEY (group_id) REFERENCES public."group"(id);
 O   ALTER TABLE ONLY public.message DROP CONSTRAINT "FK54qvll1kht7jgrly9xxuvwbvf";
       public          postgres    false    3697    215    219            �           2606    27254 (   user_receive FK64nrmwlmlyfvic3nj6egn7d8v    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_receive
    ADD CONSTRAINT "FK64nrmwlmlyfvic3nj6egn7d8v" FOREIGN KEY (receive_chat_id) REFERENCES public.message(id);
 T   ALTER TABLE ONLY public.user_receive DROP CONSTRAINT "FK64nrmwlmlyfvic3nj6egn7d8v";
       public          postgres    false    219    3701    221            ~           2606    27211 !   group FK7866qin8votush3rns0v4l5ls    FK CONSTRAINT     �   ALTER TABLE ONLY public."group"
    ADD CONSTRAINT "FK7866qin8votush3rns0v4l5ls" FOREIGN KEY (admin_id) REFERENCES public.users(id);
 O   ALTER TABLE ONLY public."group" DROP CONSTRAINT "FK7866qin8votush3rns0v4l5ls";
       public          postgres    false    3691    207    215            x           2606    25935 &   user_roles FK7ov27fyo7ebsvada1ej7qkphl    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT "FK7ov27fyo7ebsvada1ej7qkphl" FOREIGN KEY (user_id) REFERENCES public.users(id);
 R   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT "FK7ov27fyo7ebsvada1ej7qkphl";
       public          postgres    false    206    207    3691            {           2606    26316 $   follower FKb474cfe2lcwjqwf2rxf4pmo90    FK CONSTRAINT     �   ALTER TABLE ONLY public.follower
    ADD CONSTRAINT "FKb474cfe2lcwjqwf2rxf4pmo90" FOREIGN KEY (followed_id) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.follower DROP CONSTRAINT "FKb474cfe2lcwjqwf2rxf4pmo90";
       public          postgres    false    207    211    3691            �           2606    27231 #   message FKcwjkg830f0id7wmtqf5j1kj97    FK CONSTRAINT     �   ALTER TABLE ONLY public.message
    ADD CONSTRAINT "FKcwjkg830f0id7wmtqf5j1kj97" FOREIGN KEY (sender_id) REFERENCES public.users(id);
 O   ALTER TABLE ONLY public.message DROP CONSTRAINT "FKcwjkg830f0id7wmtqf5j1kj97";
       public          postgres    false    219    207    3691            �           2606    27259 (   user_receive FKdn57uen5c1wcqqkkwottsf9r6    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_receive
    ADD CONSTRAINT "FKdn57uen5c1wcqqkkwottsf9r6" FOREIGN KEY (receive_user_id) REFERENCES public.users(id);
 T   ALTER TABLE ONLY public.user_receive DROP CONSTRAINT "FKdn57uen5c1wcqqkkwottsf9r6";
       public          postgres    false    221    207    3691            y           2606    25940 &   user_roles FKej3jidxlte0r8flpavhiso3g6    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT "FKej3jidxlte0r8flpavhiso3g6" FOREIGN KEY (role_id) REFERENCES public.roles(id);
 R   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT "FKej3jidxlte0r8flpavhiso3g6";
       public          postgres    false    206    204    3683                       2606    27216 (   group_member FKm8l32kxn64rt9in09iag9dxyr    FK CONSTRAINT     �   ALTER TABLE ONLY public.group_member
    ADD CONSTRAINT "FKm8l32kxn64rt9in09iag9dxyr" FOREIGN KEY (group_id) REFERENCES public."group"(id);
 T   ALTER TABLE ONLY public.group_member DROP CONSTRAINT "FKm8l32kxn64rt9in09iag9dxyr";
       public          postgres    false    3697    217    215            �           2606    27221 (   group_member FKoivigx6k46jvewv1a88tmjskw    FK CONSTRAINT     �   ALTER TABLE ONLY public.group_member
    ADD CONSTRAINT "FKoivigx6k46jvewv1a88tmjskw" FOREIGN KEY (user_id) REFERENCES public.users(id);
 T   ALTER TABLE ONLY public.group_member DROP CONSTRAINT "FKoivigx6k46jvewv1a88tmjskw";
       public          postgres    false    217    207    3691            |           2606    26321 $   follower FKsgybcjuvib20h94jq96uv4q8d    FK CONSTRAINT     �   ALTER TABLE ONLY public.follower
    ADD CONSTRAINT "FKsgybcjuvib20h94jq96uv4q8d" FOREIGN KEY (follower_id) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.follower DROP CONSTRAINT "FKsgybcjuvib20h94jq96uv4q8d";
       public          postgres    false    207    3691    211            z           2606    25945 !   users FKt6objjexsjxjxt4tyr7bg2ks3    FK CONSTRAINT     �   ALTER TABLE ONLY public.users
    ADD CONSTRAINT "FKt6objjexsjxjxt4tyr7bg2ks3" FOREIGN KEY (profile_id) REFERENCES public.profile(id);
 M   ALTER TABLE ONLY public.users DROP CONSTRAINT "FKt6objjexsjxjxt4tyr7bg2ks3";
       public          postgres    false    3681    202    207                  x������ � �         �   x���A
�0E��)z����L��N����"��M������H���fx�|��[�cM�3�	0����mwi���5��1�f�4N��� V9�Eb�2�4�Thh-�L�T�EJhKӯŒ\TQ����8eH.������/�3ct5�/�-Ͱ��J���h���c{�$���Ż3�<���H         �   x�}�ANC1��)z�F~��8�!�EY B��?A���j#�g4�#	��Y�h'�Q0в�k�>��?/ߗ�$$K�h��: }m(gx��� Iɖ^�Zz���iV�܋K	���d���)"�iUD�h�ڣ�}��mK��V?�>��b���� ݉~�6��k����Q�xaJf��Z���7�my>x�a��>���T�@�"�)���#�Zt�b�BX�R��:C{��������         9
  x�}Z͎#I>O?E=�l����#�BB���p@�Rm{�V����=07$�`�Eb%N3�^�{�>	_�]U�%ϡ5�/*22�̦7RH��P_�k�\���knA��m�=o޿�7�NrQR�F}���o��c�ﺾ٬��a�4ҝ*>S+��	-yㅹ}��}�<�=N�t��\	�jo�e���f-;������-WZ�$Ț��}�<87�C�ru��?�+M��ӖC�n�l�{��>������ٕ$X!H2�9�����<�L��i�R���֘��:+TrRD9) )oA'���j�Y�7B��V�5^A��u<��T�BP������Iz��[d%�D\0紾E�N�%U��J " �n �q��ݰ��k=�3�4\B�ۍ�..5ĭWv��:���y<j��p�E�!�*]ÌWpd��fA���aۏZ����uDJ�̡c74��5n*�r��t+�6V߂`�Ϙ#�%2 �n}u�%����+�4+�Z���%�>S$�4h�lІ�CW�t�H���6�;�����z7ڣ���-2����o��1%	ɖ��T9M���!����Y����" =!�m�[ˡ��� ���*���ʡdP�� Z�� ̜�꼠��$��k�4c)rE�y���|��	b
UU�	��;�u�T�|�8*�yI2�0V�s+��������eh�F��	���Z���[�4�<T@������jJ�$� ���4(�a0�fZL�`��Zf�o_�;^��I�4E����fv�PV�T�e�V��z*4<��a�����a0G�6@1N��V'4sUM~U�o/�R)g�)������:k)k�j�2��H�#�!�zAM17��t�vY���V�eJ�"T,�a��J5���\S3B��~x-I�%r����8 �}������X��<��f�N�:v�,�Д��cA�$J>sqᠲ�d����$���G$V���'j�Җy7�P�q ��Y�FY�)�Cݘ�L-f�O ㄫm,����Cv�,+pR��J�7:Q�/N~����}���.���<��IPL�jw��|�{� ��|��a쎡�}��[�rl�:n��f�WMcHJ�*G�Ѫ�Rǿ�;�і�ɐE�$�7!��5I�YX�	�#����8~$S�d�[!}qi��bC�f2D1_H�o�Y�C�)��� �;j�e~�!��\��2?D�B�D	a�̡�����(:VE(En��IK�#�^	�q�H�-P�͡�-�.m��Lʐt2f-haNtщ��9l��N�JK��D��*Uգ�����"L�8m-ӑC��h�xp�@u)6x������	.�0�+4��P���8��L�;E۞�k�)]U~�x�!��t�Y~��U�d���-(��Ȃ�~jw��R�H��C��Qs$JLڅ�P���kL��\:NO^��`���qs�[�[�V%��
3�͜I;VL�Ԛ�4ws�;�7㰍A-��a����3�^�@��^r������%���QM��-��9�|�R�c>	�H��P����%�o] ��!$�kL��YM��(Z�[�dw�|MPJs:"e64C�N3�/�e��bO�������]�4���0��8�܆��xEc<�T:��f��b~ء�GuU-�ǀ1s*��|��_g���GʗťB�H
���.�)��J�l� �J��ފ34�bP\���D%�jh�]If�I�b0���kh�ޣ1��ԥ
Keo[C��I2iA��V�9-���?��lO�?��f�͡<�@�L/,�{�Lɡ�~7�`��x�!tP�9-�&�CpI�F�%3C���_}zYzܽ�L'�����tc75_�Z��:��b�]Xk����Q��ws3G�y��	��2�r��м^ݩ�o������}۬_����W�����x����m����m���۫�9��z���fx߬��a�>�� ��ۧ�i�ͩ{����T�_~���}��o~�/��}���32���<w����o,�l��n�tl��O�ms<??/�Bq�o�o�0b���N^�#:^�;D��%jhN�QK���x�d��sd3�Ԉ������4TM�����_�y�V��E,x驎�:�v��y�S��x�%�6���*I�^3^}�V+�ch~�����JY�������b,���"�%z[�p��a�&���2>��Wja1W߄N/���,�\DARUp*����o��i��������А�M�u�əʐ�qb419[R2�c"���K=��I�R����VQ@�ᙦ��JR��8��y9���mִ�j�e���W����f3/�Q�l�RK+�P~�m�AaAu(>,i������sժ%i�e�ɡ��w���O�g;���7�*%��	�𞴰U���x��M(M���%;�g+�U07�ؑO�>$kG�8ِ	Kj&h����t�dMh#cr��������AY�:���7��F7���Y���n�����Š�|r5����X؁f'W@���~����P�dsS]/F���UEF¨��ȡ�H���T�^����bA��U�ᛤFrY��ʅf(���5�������?���            x������ � �         +   x�3���q�wt����2�p|��]���!��` ;F��� �
�            x�ŜYo�X����E�=�D�C�0��f�%3c0l�O��ة��J�U�K��"�w��s�gX,�~�A@��?`�$�!�	EQ
F����-���(D�VH��ؓRH��������8��X�.�e3�e���)�	��J�t���.�X"�Y�t D;��Q�zڻpD]���dݰ�y��aӮ�L�xX4� �l���U�MSN��D�u[GL\B������Ұ.��?��G��I�����?uX6���Ǜ���6�F�o���7�|:7��7&�������6�����6���ǇMY�c�	��ڔ[%hQ{k��Y�n21\���"�q��2�2�KO�V�J|�	q3�
��gT女��;�����D�F�e	�"�+�s����	�1�Z�|�-���h�i�m\G s�0��V�� ��J��f߁� Sa:'��x���F��8�Y�Q�f[a�q�B�ަ;!���)5�����Vw8!N�I����>��-jD�(�tͅ�t���-ˉ�z� ��\Z�݊t]�	@5�	��;!�+��H�(�J�W	�~)tP-��gB�Y�6���{&LI���[�D��d8�V7�=^r��]�P�4K
�)�g����j�����n7h[��z�B�q���M-߹��ܼ��f�N!���F���.8�:�bҮ��[�ۺ�`���>��F���4[�u/�a��d�(�d+!��5=c�8��%	��J �K�Ê�a?rZ_��䅾�\��D�HE��qgq{�� :��ش���,�>}��dnb]�"�<�Yz�!�1�jr5[X��g���6���k���$[�b�gcrn�M�:7@=\����"�������I�;	`,�(�D�pGC�&ݧ̱rξW����+!�+P�B�(~]H�t�L���Fب�F�Ot��.�Jt���#�Ⱦ>����3<�=B�3��:`�a��pg@��T�i�~ .An�5�O'@I���$��9t��6��P�M�Ji-�4Q˚ K�nL�C�\�J�Q#��\�-V�n��ǌ�ϭ^ORcS9[�	��^�ID�@͵����п����	 �[{�q��� �wt�I�������X�-k�kJ]�i.f0�I�`G���z9ʷ�D�60N��B��a�m��FF�G���֋i]8픤D��!r���|^T={M�Fh���@��@�_ �i��vM��H�>�.{�@�]�S����A�u0QF��[.�� �\#�F�)a�or�h<�h�&fR��ݥZ�eĖ��,6w�E�̸��{֖{����@$S�K�Q�E��(��	V�|M��f����q�;��I�X�1h�U��"�1����k����Σͱ2+:.D>�d�����t,Wanh�B����/�3�G٤`K��D�����S��;X��m)2uH�1~L|ݭ�h�Eښ��23^ҥ��UpSH1�m9�3̸�����ܹ��JC�� �!�	$ �ߗ�r��'
{�{#��P�䐟��[����;�#���ҌZ?	�?V�����p�6Ȥ��Eh��'�b|�J���`�jBj�� y�H��Ɵ��?��ܦ;!�M�u#�8��`suEn`):F�p���r���3���������}����	��d�u�\±��Ga��k��1Q��(���k8���~t�(�/����֒��̇2+KN=!\�ͯ�q��W�.N}�=�\}NI�ڪX����4�H��K\d�]f�&D��ɝ�Ps
��p��5Xi�5�`ӝ�Ŧ:��:�ȁj61N������b�i@�J�iKD�p׋ʛJ��V�[����~ ݝ�Ö����"���g�r�1��N9L 0��/}C�E�k����B�غ6|�0{���{p�!<z�-�c����^:Ȼ"oU�3"O��M0���	����ڍ�1ch���}���nÊ}�M/�<l�f+�Tr#�i����35N~Փ����,N.��ɓ�,U[�@�g��Ofuf���R���i�^QJ�x/2����f� �$������?��G��}��,���ͦ#��?ƣ�X�^�>vZ���I"�4�3[��+�v�ջp��%����3�t��T�#����9���gT���ȋM��=n�m��0�]���|{��s���'�v��R�:)���#9�˭��]�'ݥ�Z��G�r%}�w���g�n����A�0}_B���/���<�Ǝ�_?#�V^v]�^s��9_���������o��������=�12HKg�y�CG�T/J�I&�a���+��e!�=�|�M+"�bSvro�X>㶜�qG4�`�ijS��r����й?�ȵEI8C1|r]�j�t���](t�F�h d��!Ž���9��g{lCD��KD�I�/�jl��kMyE��q���_���KS^P󼧄�6k�at)�DFp��;�r�����dh��ؙ��!l����.���m����OI��{?�-}TK�ŭ�&��s�1�����d�F�9�K��jε�Vrl�
��W��7nl�pO�R���G��u�A��ȭ�2�;�Џ4�}�}�	%P��u�y��_*��JE6�L��.�z�&�V�r�z�r�X<�Kf֬��O;��q���1�/a��X�{[��zj���a=p�3I� ��d/v�"���
�(�]�L�]���NM�.�g��Z�
1��\�*��#���}�*Wa;�L&c�8�`N�p{����{��'GH~_��ʋ���2�@��BS�5vg��������)�AK�-"X7��|X�)�ĂͶ&���
%�d���ECU���T)�fa�n(���G��@��&:?�	V���V24;�s�20�.{V�V�<40*Q��q�l�R`�j˞7����j�ˡƲ���Nc�<| ������ �!8D��K藾����;�F�� ��� �T��n�v��U҅�UI)��IO�nN3
m���mu��S���\Y�3���V%��YNE}d�"6EV�=������tG��bSeܸ�f�r�����v�gN�1'3�:�:z���zC�я(�O��i����Svo���a�s�I��c-�w&�+ �>�!IR�yC���	y�R�[��/U�n^Y��=D�#�0�yt�a�F�`Wv6$�N���Qzi>����U��;�h���ϓ��C�۵ag���&�E��gd��iEd�_l�n�f���P�իO�l�z� :�SW���֡4e�q�=��'7� �/
�K" a�D�SW4ɻ[��!���w�������A0��/�_��	D�o�Π�����u�Y��}y�4C��%�b�]���d2AC��e��ezp�{.��j���2�z�sH��>�@��\��f�1�HR���}ZO�1v�jq/v9iT�!����3 �x衯���|m�	Bp�9޴["��m�q{4���v�.���%��p�e٭έ�����D�? j���� "	�}	�S����#�VG�҆^��,=���U�7�<t#=�b&���I?]��/���s�&�W�T(�쎩�h`��i���&�Ui8�L4z���s��qp�4P>�,}V��,�W�����nٸ4,�>�	~�ׯ���
���t����+�E�X����,��Ŭ�ù���}S�g��C<���# �/=0�&��+�������aX�DT-�/����<K;�)�Ӆ��&��J2{��x�A�:���!�=�˳v���!�##ȗŝ�xn��|H>���K����]�����bO��1?��A���}�U{���s��K�8���u��ܳލ�=�����7�G�9�iҠ��"8���#���W�ź���H���w�oz��D����{���������	D{�8j�LVUC��7�-x]$q�,�He'�`�q��^X��cs��6�QQu8����s�Ʋ�Q��>���kEe�^�7��� �t����x�-�6P�A��4������
�oDL9&�fx�v��F�wiy�a����۔��� 7  y ��!G�#��r����W	'�_��4����V�ˉ�Ҡ����N��6S���Rb�	qolPƭwG��k@�W.��:Uo����n��uOR�cP�1UKb���_?2�16��_lR�ēJB���M���f�*�F��m�����雾戄�_Ș���(�5�|�O�\Ё=5�i�y&��� ���5e@
����+"_�����c��V��l�ƿ����(aG�cC;A��,�Z܊nS�9m�hV��h�W4�2��� �采��2e׃��E���<N�c}�������tG��Im ��va+�����K�pE!S�9�ܮ�h�&N%�i�_v�#"���';�N�z�R��|����-������D ����O�/���9n�^46z=���e�겶�`��v�R-PF�S��*ŕ�by@����e/�QG&���?�`l�=l�/סQ 5�k�цSW�^5�2�Gؤ��D_m��T�9�FU��m��i9^'v1�tm����9�x�7����YsK�1
�)����H��h�F���d���{Mv��B#ޗ�!�"!��=ﱯ�����ڶ��|����lf�1�k�~F�f.�R�V���l�7s�̶��|T�L��6>ު%��83Y�V�>	�ڦ"_l�����x������A%f����B����r�6�Wb�3He���ҟ�ؖ��5ՙ��Td�)�$�|�!�@�s��"�_�^�&����=vp����ڊ��>�K\�*����B�N+���K�8����.62y� hE�Qb�!.V$��sSa\6Eď����q<�;G�;4���Ė�#캣���518HQ��=cm2&8�qw8��W2}.<� 7������O I�-UJUA.z�׭V�`H]�Y82%P���_�y��A�i�����o��ҺL~��=v����hR�u��~
�����f�<f*��nv*o;W�7�>ԱQt�Ie�Ħ�=��4���f���wHa��V��n����������Ŧ��$/Ho�i����]�3�`��Mb�
 ���~��`�S��q$����rv�kx�@ަ>�h�Q��~�ֲ�� ���|�]���ի/�+ot$+ �R˅W�����M� ��F���Q��Ƣ9T�+�`Z�.���>%���>qՓzS�3�:�����ZFV6�3������M~���"�A�r( �T9�t|q�2w#�����#�Ra0►5�)��3��U]N^��d�o���Z��9����;fo�Z����n��ۡ��[�����pa�c��Y>�N, �Mw�
���F���X�oru���}Ս�͠2�W�Ū/):}7��˄H�h�:�⧥�ԥ@.��y��E+��$|�ZF!G+�(3�F����v�)�d�xF�ݦJ*�~k�ۻs�����4�7-���o����~^#�a%_'�7���|�����S��7��R���%Gn����a��.Р����	k�ضҵqXI�T�{��C@��O�����N�ǀ�w����j���n>Ʈ*/vQ�9I
�"W��H�����茝�7���ln�yo�}��F��--^,l�v�&OXr��T'�������5�����:ͼ!}C��*���V����	���2���s����n g.bi�Û]�PWo������Јp�ᵖ�s�ե�Z�s�����+���9������M��_�T�]r��vȵɐ��(�f�Ȉ!:%����DGi���\��gl6-{��⁢����PKR�t;�����s�R8��F��+"_$��b���l�͏塀*p���s�L���-��n�p,��3�U�� bg��ʸ �`�����eP@�zTo !_���9�?nӊ�E����]lDk0r+{|S��n��X��Д����H���
"�W�`�kx U���z1��ʶs!�ܦ$�(:.�#���S�>���H�@ �}��o�/����J(k��Y?;�*�~��3����C�WV��v	�#�ã�<�l�uz)��EA3�c�'�'ρp�8�5Cᛰ�Q�Z���3� ��Ea����3��-�h���d�СB�c ����y�np����S�6q))�P]���M$i�s)�
D@���X����:��cE�g{�Q��q��m��7	�咦���Sh�QYQ�u�k�I0�y��JIr�v'$ԝ� �>�Ar`;�j�uz�/��?�
kGK�b�#�;g,�O�˳��8����O��4%���?����Vb�ŵ#�m��	���}hb�ٶW��Yǁ�siwQx�Yp��7�Ȱ�B/����yN5��ψ7�9&���1y�L޹�!��B���Wb^$���j�cj���T������q�촂�;�qm�I_R`Ldy�G|v�*���b�,d��,b2'a�X�KP���X�"�3!L�W9,�;�^�v�QQ^������A+ǈ>�6�!-X�Z8�J�6��T{;�v���¦�l�l�m�AW�{��x�Х*���R@���@�"��
�(L_��?�g|mӗ��m��}�Q���(g�lf�9-��-����I�*<�DM�[�Tu)sH_��b��(,����Mlн/xց�3�Qx@G�E$��8����^ܪB'�|��nW���3����PcH�N�z�6gD��]P4>޼	,�`�ɤ�u�"E-�/}�.��<j��3J>���R���/�&ߔ��e�s�V��X_��F�7�)�C�� jW�:���d�}�(����\�������%k��5��g��m��*����IS���c?MhT/�@��M��ؤ/��6M<e�jU	��}�8�+7LtX�GQ ���Pm2���1t?d���ZI)0#�5����-��$�~�\p��=0t������$�0���@�O	��]�R�?j��4���^�oɘo!	v�>MC�喃,y�f����T��̒��^��ьw�Z)K�&{C�x�C�`6���-�S�Gإ����؅))黣kH'��/�,l��A8��A�F��\v���!�G!v�urtm���Z)bP(Xd�BC8�ݽ�������Y�           x�}Z]�*
|>�����O��E�
���	;
N^��+�%b������������M���������#��k�zx�! �䍕� _�Wέ����<�	�B����~$JjPޠ!�����/�<���o�("P=�|.��Bh2�^���@�W������O3&es���.#����ߔt�2]���d.� V���}�\*�W�$ԉ����7����B&�*�=��.ȉW7G�$ԍ@�:���A&A:
�<���|���#(�%_/�)qX!�PGB��������������t�1u��~3j©�Ư�I�/q�,>$��o�'�%k�9�muE�$@%<�7����d�K<���N�5~�LB}�y��)�� �$ԗU��^��"*o�I�7#�kE�j�s}�LB���S�1�2���h�'���}⌬�I�;�x��4�Y!�Pwf�N)J)_�ZM>���/����\�-���2	���𨜮Z���2�����	O
4%lo�	dC�Fy�����A&��x����(�I�vo�2	��sx~�_	{�
��z3xXxؤ$�D!�+d��x��$�!,�2�����̬��lT����z��7Gm����$B�Vi�$�MJ�*�6��2	���I�U:�ڻ"&��$�?��"�T�2	�&�pi�5RQ�V� �кI>��#qOM��(V�$�n��&ɞY�*�����C����ְ�W�$t7��+U1oa�LB]Iޕ�:e����2����w��P:ABs��WWW�9c��l(ǒ��^���ZIX}27�$t7�.<�=(���A&��y����PAf�2�q�	<�i|Iq���B&���+���8@YZ(�C��w�����a�L�IO\jup���d������
��D�@�w��4s顆���O�G�&mu����r==I�bi�zp�
�D�0N<�����ASB9dLx��1�4%�SB�z��U
s�It$8s�zM5�b��������h���a�b�F�R��
٫���F�|P-�M	���ͬ�Q��gd��~bA�m&_ )K�L?�I�+���\8a6hJH ��Y�%P-6��𩇀�.�&�!�?��v��XY���f��L"k&��3���mД�L(���C4%dJã�d�.��0U+d�����EY��B��~�LB���Y��Q1��Ih�n�7�sH�3̵����LB{n�zHxc��*�uP_!��G�!#i#��A�LB��k�O<�G�~�x {��A��O�997�!&����M�9��b�)A�������)4%$��(�G%�T� W�$t���,<�}�����j�LBWHO�,S�N�?�$t��yc}�t���B&�=V�d}!QK���@&�]V?L����Д�O?��t�mH�Q��)!�/�Pz�8�+d�e�0������)!�w��9j�1?hJH �QE���5��B&�}V�)�~��� WhJ�R�� 6����
��>�ܸ^H[
�B��}�C�B���l����HB�n��ݿBSE#x�t�B_C6��@�D���$ԋ[�a�n�i���C"R'���qx��&�%x�L#�Y9s���(!�45tV|ތؒ����M������l�i��v`#69��o�ԐXºTb�����8~�Ԑ��F3Q�68L����H����z�)9���(G�4��/�d�ff� 2�6�9�z� ��cو��$�2�Q;�+�Q ��qCSCg��
�Y�����t�&2�4�`;���T�i�����v���CSCb�/�~�.�9��ohj���#e�Ɇ^�{p��X�Hd9��0454>�tz�g7454���\J���LG,��e�@�+
���aE��guC��:�Gk�LT} �5}�.�� �s����I>=���0a45���6拘Z:i� �5=\*D�z�)}����\*�����M����(ɽ��i��%�tY:���BSCc	A��;��qCSC�%�t3�j_��@�QF,�H����a>�ԐX(�t�c�`���Ł��B9��W�4��>���M&����i�O(����_@�W�4Ʒ1L@�|�����(�qu���&#���nф��x!�%���EF)#oU�da��a�6��od�Wq��9�V�!��X����*6=��P�6�k��W���Q��ݠ[d��}�v�Z�(�|Z7�={&�{�0�O��"î���75�,��G=����/��(�,��� ��VW��ǇwȘ\Zt��b�\2fn�,wk�[��g{S��B�3=��Ŷ���"í��1����֫��"c�[/��T��� W�'"9Y��&�^�>"B?�<�3��"}��%}8t��&v�lL*㛡%{��E�Wf�ǘU� ��v<��-2����N�T����v+B�����0��윶���Dtv�����$�\~�~"�oe��r�ڠ[d���w�1I�r<���D4���!�Y��~"�!�w�0�x�-�~��2��z�
SzS�w��{�[d�&jplӦP_8�)^�[d�~5���Oao�-2:���~�Q��4E��#��Q+�v�r�n���Uo��o�BE?�t�ط�>p�\���O��"c���qYߨ�t�q�+t���S�cY?��Z�ca�
�"clޱ�z���n��"d"y�h�q�)�qoa�+��ؼc��E��!]+��pv3�}�NM���E�O�LYe��A�n=F�{}>���p         "   x�3�4�2bc 6bS 6bs � �=... U�}         d  x�}�[W�:���Wx1s�4Is�j(��AT�5k���V{�8�����r`��UJ�4i�����=#�� ���L@�%'�(DPqȈ���].L"枝�����_���Y���A��~�`�|Q�E_4�������Ss�	�������&�Zl������p�rw��o�V��~T��b��6EU�,��`<?�S:Kc�ü�*sq���gU�UAX9H:H8�dv@`�H��.�s�u�70iP],$�K�a��r�:
�pTa�h��h�Z�A��Y�մٹr��
4)�O�܌�}��wc]�Q�|�V#�0�|ESq��d,_g��NjyTQ���];�"C�`1��	���bL� <gǋvͺ�T��&D��������@R$8�{����4x�/�g���g=���dQ�S��G����Ç�!��ן|�x�����g�E��J�� 4:���I�}�=�8ա���mZ�"3��Fڵ���z�<�YX�;���~|��E��W�&��`��bL�	$�s�1���2�]O��g�:vVB" �د"��(�è�X|���MdBpBNCF}��=�D
)5O�;��D�լ�ҫ�V��\f��^�&E5ߌ��n�r���$C��}����A��<�x40�&aU�0L?�6_���D�t�"��*W5a��&�H�b.(&.B�Hez� 3�96 �X0�]��
xE���JI.�+�������hc���%$!!¸�1O�@*ĩ�����cx[��K���?�E[��g��F$!>|�lo��ݮ�GP<�ZW�������u?�"0ѹotg����0�v����E����"i��1zk��X�(`6Bܓ9^�Y>Ő
A؉�uG�1!'H�RÌ��=�|S�� #>��7��vz��u�z�N_u&��O./�^q������̓��7�6�aT{_�蕮tL�(���͡P�q�g��0`d����c`�j� ����#�GCN�Q�`f���a�������)S�'����i$�k�����^�%oUgp��w�Aw�Z8�t�`�yX�^��[s��7����>��韕�����<q�C�4~3}�֘ Y"���_�~:]bD27��-�=��?�1�FAY`e�|t���� ��r�*&���_!0o 
��\��9�?Ug���04%J�>�=�k��J��������J-��������hy�+�,^�����b�=�^<��o`8_�y���2��Z��rZ��qۧ��J�a9ͮs��i�{��������{�e�
�ݗ�,|�͵[��m+l��f粜ތ�9M�7M.Z�=�r��"�NAW?I�'1�f��G������Ǻ�     
PGDMP     5    5                {            apiRestDevsu    14.2    14.2 #               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    519770    apiRestDevsu    DATABASE     m   CREATE DATABASE "apiRestDevsu" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Colombia.1252';
    DROP DATABASE "apiRestDevsu";
                postgres    false            �            1259    519772    cliente    TABLE     �   CREATE TABLE public.cliente (
    clie_id bigint NOT NULL,
    clie_contrasena character varying(255),
    clie_estado character varying(255),
    pers_id bigint
);
    DROP TABLE public.cliente;
       public         heap    postgres    false            �            1259    519771    cliente_clie_id_seq    SEQUENCE     |   CREATE SEQUENCE public.cliente_clie_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.cliente_clie_id_seq;
       public          postgres    false    210                       0    0    cliente_clie_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.cliente_clie_id_seq OWNED BY public.cliente.clie_id;
          public          postgres    false    209            �            1259    519781    cuenta    TABLE       CREATE TABLE public.cuenta (
    cuen_id bigint NOT NULL,
    cuen_estado character varying(255),
    cuen_numero_cuenta character varying(255),
    cuen_saldo_inicial character varying(255),
    cuen_tipo_cuenta character varying(255),
    clie_id bigint
);
    DROP TABLE public.cuenta;
       public         heap    postgres    false            �            1259    519780    cuenta_cuen_id_seq    SEQUENCE     {   CREATE SEQUENCE public.cuenta_cuen_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.cuenta_cuen_id_seq;
       public          postgres    false    212                       0    0    cuenta_cuen_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.cuenta_cuen_id_seq OWNED BY public.cuenta.cuen_id;
          public          postgres    false    211            �            1259    519790 
   movimiento    TABLE     �   CREATE TABLE public.movimiento (
    movi_id bigint NOT NULL,
    movi_fecha timestamp without time zone,
    movi_saldo character varying(255),
    movi_tipo_movimiento character varying(255),
    movi_valor character varying(255),
    cuen_id bigint
);
    DROP TABLE public.movimiento;
       public         heap    postgres    false            �            1259    519789    movimiento_movi_id_seq    SEQUENCE        CREATE SEQUENCE public.movimiento_movi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.movimiento_movi_id_seq;
       public          postgres    false    214                       0    0    movimiento_movi_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.movimiento_movi_id_seq OWNED BY public.movimiento.movi_id;
          public          postgres    false    213            �            1259    519799    persona    TABLE     )  CREATE TABLE public.persona (
    pers_id bigint NOT NULL,
    pers_direccion character varying(255),
    pers_edad integer,
    pers_genero character varying(255),
    pers_identificacion character varying(255),
    pers_nombre character varying(255),
    pers_telefono character varying(255)
);
    DROP TABLE public.persona;
       public         heap    postgres    false            �            1259    519798    persona_pers_id_seq    SEQUENCE     |   CREATE SEQUENCE public.persona_pers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.persona_pers_id_seq;
       public          postgres    false    216                       0    0    persona_pers_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.persona_pers_id_seq OWNED BY public.persona.pers_id;
          public          postgres    false    215            k           2604    519775    cliente clie_id    DEFAULT     r   ALTER TABLE ONLY public.cliente ALTER COLUMN clie_id SET DEFAULT nextval('public.cliente_clie_id_seq'::regclass);
 >   ALTER TABLE public.cliente ALTER COLUMN clie_id DROP DEFAULT;
       public          postgres    false    209    210    210            l           2604    519784    cuenta cuen_id    DEFAULT     p   ALTER TABLE ONLY public.cuenta ALTER COLUMN cuen_id SET DEFAULT nextval('public.cuenta_cuen_id_seq'::regclass);
 =   ALTER TABLE public.cuenta ALTER COLUMN cuen_id DROP DEFAULT;
       public          postgres    false    212    211    212            m           2604    519793    movimiento movi_id    DEFAULT     x   ALTER TABLE ONLY public.movimiento ALTER COLUMN movi_id SET DEFAULT nextval('public.movimiento_movi_id_seq'::regclass);
 A   ALTER TABLE public.movimiento ALTER COLUMN movi_id DROP DEFAULT;
       public          postgres    false    214    213    214            n           2604    519802    persona pers_id    DEFAULT     r   ALTER TABLE ONLY public.persona ALTER COLUMN pers_id SET DEFAULT nextval('public.persona_pers_id_seq'::regclass);
 >   ALTER TABLE public.persona ALTER COLUMN pers_id DROP DEFAULT;
       public          postgres    false    215    216    216                      0    519772    cliente 
   TABLE DATA           Q   COPY public.cliente (clie_id, clie_contrasena, clie_estado, pers_id) FROM stdin;
    public          postgres    false    210   l(                 0    519781    cuenta 
   TABLE DATA           y   COPY public.cuenta (cuen_id, cuen_estado, cuen_numero_cuenta, cuen_saldo_inicial, cuen_tipo_cuenta, clie_id) FROM stdin;
    public          postgres    false    212   �(       
          0    519790 
   movimiento 
   TABLE DATA           p   COPY public.movimiento (movi_id, movi_fecha, movi_saldo, movi_tipo_movimiento, movi_valor, cuen_id) FROM stdin;
    public          postgres    false    214   ')                 0    519799    persona 
   TABLE DATA           �   COPY public.persona (pers_id, pers_direccion, pers_edad, pers_genero, pers_identificacion, pers_nombre, pers_telefono) FROM stdin;
    public          postgres    false    216   �)                  0    0    cliente_clie_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.cliente_clie_id_seq', 19, true);
          public          postgres    false    209                       0    0    cuenta_cuen_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.cuenta_cuen_id_seq', 12, true);
          public          postgres    false    211                       0    0    movimiento_movi_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.movimiento_movi_id_seq', 14, true);
          public          postgres    false    213                       0    0    persona_pers_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.persona_pers_id_seq', 18, true);
          public          postgres    false    215            p           2606    519779    cliente cliente_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (clie_id);
 >   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cliente_pkey;
       public            postgres    false    210            r           2606    519788    cuenta cuenta_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (cuen_id);
 <   ALTER TABLE ONLY public.cuenta DROP CONSTRAINT cuenta_pkey;
       public            postgres    false    212            t           2606    519797    movimiento movimiento_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT movimiento_pkey PRIMARY KEY (movi_id);
 D   ALTER TABLE ONLY public.movimiento DROP CONSTRAINT movimiento_pkey;
       public            postgres    false    214            v           2606    519806    persona persona_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (pers_id);
 >   ALTER TABLE ONLY public.persona DROP CONSTRAINT persona_pkey;
       public            postgres    false    216            y           2606    519817 &   movimiento fk6xl71jqneqh1w8b70q07gn728    FK CONSTRAINT     �   ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT fk6xl71jqneqh1w8b70q07gn728 FOREIGN KEY (cuen_id) REFERENCES public.cuenta(cuen_id);
 P   ALTER TABLE ONLY public.movimiento DROP CONSTRAINT fk6xl71jqneqh1w8b70q07gn728;
       public          postgres    false    212    214    3186            x           2606    519812 "   cuenta fkehjpnc0n4ysabogqvnyo8sy1b    FK CONSTRAINT     �   ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT fkehjpnc0n4ysabogqvnyo8sy1b FOREIGN KEY (clie_id) REFERENCES public.cliente(clie_id);
 L   ALTER TABLE ONLY public.cuenta DROP CONSTRAINT fkehjpnc0n4ysabogqvnyo8sy1b;
       public          postgres    false    3184    212    210            w           2606    519807 "   cliente fkgqqrp44kcfkhkndbvcbcegcl    FK CONSTRAINT     �   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT fkgqqrp44kcfkhkndbvcbcegcl FOREIGN KEY (pers_id) REFERENCES public.persona(pers_id);
 L   ALTER TABLE ONLY public.cliente DROP CONSTRAINT fkgqqrp44kcfkhkndbvcbcegcl;
       public          postgres    false    3190    210    216               ;   x�34�4426�)*M�44�24�453���M���)�o�eh	Voj������� ��         `   x�5̱�0D��Ŏ/qJ�
��,���$Nh�ޝ���7Y�
'M)�N�l���
�Eά3�¼�t}���Na�ˌp�0l`eY'��P��#����?m�b      
   }   x�]�11k���Xk�v�K�+��%��+��YiF���*��/@��79�*I �ܷ���� $Zƿ����� ���n�QuW�Pbі3fm�-���� 	K������<���0%�         �   x�m�1N1��z|�9@�b{�v�hE�	JG3,.y=�ލ�����0-�~�ӯ5��R�?�R8O|�[x�)NK�p��!��]0���)m`?�g;�F������0�G���4�]v}��ŷ5�4~��r�&�i���T��/�w.x���i]h��$�_��xA۹��uiy|��/*�_	lۂ�M��R��8M     
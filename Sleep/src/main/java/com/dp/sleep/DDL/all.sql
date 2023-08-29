create table public.consumable_equipment (
                                             id bigint primary key not null,
                                             remaining_working_time character varying(255),
                                             replacement_required boolean,
                                             work_resource character varying(255),
                                             created_by character varying(255),
                                             created_when timestamp(6) without time zone,
                                             deleted_by character varying(255),
                                             deleted_when timestamp(6) without time zone,
                                             is_deleted boolean,
                                             updated_by character varying(255),
                                             updated_when timestamp(6) without time zone,
                                             user_id bigint,
                                             foreign key (user_id) references public.users (id)
                                                 match simple on update no action on delete no action
);

create table public.converted_data (
                                       id bigint primary key not null,
                                       date date,
                                       sleep_duration double precision,
                                       sleep_quality character varying(255),
                                       created_by character varying(255),
                                       created_when timestamp(6) without time zone,
                                       deleted_by character varying(255),
                                       deleted_when timestamp(6) without time zone,
                                       is_deleted boolean,
                                       updated_by character varying(255),
                                       updated_when timestamp(6) without time zone,
                                       user_id bigint,
                                       foreign key (user_id) references public.users (id)
                                           match simple on update no action on delete no action
);

create table public.input_data (
                                   id bigint primary key not null,
                                   date timestamp without time zone,
                                   humidity character varying(255),
                                   temperature character varying(255),
                                   created_by character varying(255),
                                   created_when timestamp(6) without time zone,
                                   deleted_by character varying(255),
                                   deleted_when timestamp(6) without time zone,
                                   is_deleted boolean,
                                   updated_by character varying(255),
                                   updated_when timestamp(6) without time zone,
                                   user_id bigint,
                                   foreign key (user_id) references public.users (id)
                                       match simple on update no action on delete no action
);

create table public.meditations (
                                    id bigint primary key not null,
                                    created_by character varying(255),
                                    created_when timestamp(6) without time zone,
                                    deleted_by character varying(255),
                                    deleted_when timestamp(6) without time zone,
                                    is_deleted boolean,
                                    updated_by character varying(255),
                                    updated_when timestamp(6) without time zone,
                                    direction smallint not null,
                                    file_name character varying(255),
                                    name character varying(255)
);

create table public.roles (
                              id bigint primary key not null default nextval('roles_id_seq'::regclass),
                              description character varying(255),
                              title character varying(255)
);

create table public.users (
                              id bigint primary key not null,
                              created_by character varying(255),
                              created_when timestamp(6) without time zone,
                              deleted_by character varying(255),
                              deleted_when timestamp(6) without time zone,
                              is_deleted boolean,
                              updated_by character varying(255),
                              updated_when timestamp(6) without time zone,
                              address character varying(255),
                              birth_date date not null,
                              country character varying(255) not null,
                              email character varying(255) not null,
                              first_name character varying(255) not null,
                              last_name character varying(255) not null,
                              login character varying(255) not null,
                              middle_name character varying(255),
                              password character varying(255) not null,
                              phone character varying(255),
                              subscription boolean,
                              role_id bigint not null,
                              consume_id bigint,
                              change_password_token character varying(255),
                              start_time timestamp(6) without time zone,
                              stop_time timestamp(6) without time zone,
                              foreign key (role_id) references public.roles (id)
                                  match simple on update no action on delete no action,
                              foreign key (consume_id) references public.consumable_equipment (id)
                                  match simple on update no action on delete no action
);


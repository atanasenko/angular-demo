create table if not exists student (
  id bigint not null primary key auto_increment,
  name varchar(255)
);

create table if not exists course (
  id integer not null primary key auto_increment,
  name varchar(255)
);

create table if not exists student_course (
  course_id bigint not null,
  student_id bigint not null,
  course_grade integer,
  primary key (course_id, student_id),
  foreign key (course_id) references course(id) on delete cascade,
  foreign key (student_id) references student(id) on delete cascade
);

create table if not exists assignment (
  id bigint not null primary key auto_increment,
  course_id bigint not null,
  name varchar(255) not null,
  course_weight integer not null,
  foreign key (course_id) references course(id) on delete cascade
);

create table if not exists assignment_grade (
  assignment_id bigint not null,
  student_id bigint not null,
  grade integer not null,
  primary key (assignment_id, student_id),
  foreign key (assignment_id) references assignment(id) on delete cascade,
  foreign key (student_id) references student(id) on delete cascade
);

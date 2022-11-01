Model Documentation
===================

Member
----

### Relationship

| Model    | Relation   | Description |
|:---------|:-----------|:------------|
| Question | `OneToMany` | Children   |
| Answer   | `OneToMany`| Children    |


### Schema

| Column    | Type       | Description                                                         |
|-----------|------------|---------------------------------------------------------------------|
| username  | `String`   | Username                                                            |
| password  | `String`   | Password                                                            |
| email     | `String`   | Email of user                                                       |
| is_active | `boolean`  | `true`(default) or `false` indicates whether member is active or not|

Question
--------

### Relationship

| Model | Relation   | Description |
|:------|:-----------|:------------|
| Member| `ManyToOne`| Parent      |

### Schema

| Column      | Type          | Description                   |
|-------------|---------------|-------------------------------|
| subject     | `String`      | Subject of question           |
| content     | `String`      | Content of question           |
| create_date | `Date`        | created date of question      |
| modify_date | `Date`        | modified date of question     |

Answer
----

### Relationship

| Model | Relation   | Description |
|:------|:-----------|:------------|
| Member| `ManyToOne`| Parent      |


### Schema

| Column      | Type          | Description                   |
|-------------|---------------|-------------------------------|
| content     | `String`      | Content of answer             |
| create_date | `Date`        | created date of answer        |
| modify_date | `Date`        | modified date of answer       |

Question_voter
----

### Relationship

| Model   | Relation    | Description |
|:--------|:------------|:------------|
| Member  | `OneToMany` | Parent      |  
| Question| `OneToMany` | Parent      |


### Schema

| Column      | Type          | Description                           |
|-------------|---------------|---------------------------------------|
| question_id | `Long`        | id of question voted by member        |
| voter_id    | `Long`        | id of member who vote the question    |


Answer_voter
----

### Relationship

| Model | Relation      | Description |
|:------|:--------------|:------------|
| Member| `OneToMany`   | Parent      |  
| Answer| `OneToMany`   | Parent      |


### Schema

| Column      | Type          | Description                           |
|-------------|---------------|---------------------------------------|
| answer_id   | `Long`        | id of answer voted by member          |
| voter_id    | `Long`        | id of member who vote the answer      |


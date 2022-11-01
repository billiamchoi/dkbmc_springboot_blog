API Documentation
=================

This API documentation has been tested using [Postman](https://www.getpostman.com/).

Contents
--------

| API                                 | URI                                                   | Method    |
|-------------------------------------|-------------------------------------------------------|-----------|
| [Sign Up](#sign-up)                 | /api/v1/account/signup                                | POST      |
| [Log In](#log-in)                   | /api/v1/account/login                                 | POST      |
| [Create Question](#create-question) | /api/v1/question                                      | POST      |
| [Vote Question](#vote-question)     | /api/v1/question/vote/*:id*                           | POST      |
| [Get Questions](#get-questions)     | /api/v1/question                                      | GET       |
| [Get Question](#get-question)       | /api/v1/question/*:id*                                | GET       |
| [Modify Question](#modify-question) | /api/v1/question/*:id*                                | PUT       |
| [Remove Question](#remove-question) | /api/v1/question/*:id*                                | DELETE    |
| [Create Answer](#create-answer)     | /api/v1/question/*:id*/answer                         | POST      |
| [Vote Answer](#vote-answer)         | /api/v1/answer/vote/*:id*                             | POST      |
| [Get Answers](#get-answers)         | /api/v1/question/*:id*/answer                         | GET       |
| [Modify Answer](#modify-answer)     | /api/v1/question/*:question_id*/answer/*:answer_id*   | PUT       |
| [Remove Answer](#remove-answer)     | /api/v1/question/*:question_id*/answer/*:answer_id*   | DELETE    |


Sign Up
-------

Returns new account.

**URI**

/api/v1/account/signup

**Method**

POST

**Request Parameters**

| Key                   | Value      | Required |
|:----------------------|:-----------|:--------:|
| username              | *[string]* |    Y     |
| email                 | *[string]* |    Y     |
| password              | *[string]* |    Y     |
| password_confirm      | *[string]* |    Y     |

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 201                   |  OK       |  


```json
     {
        "status":  <상태메세지>,
        "message": <메세지>,
        "data": {
            "id":        <데이터베이스의 member id>,
            "username":  <사용자아이디>,
            "email":     <이메일>,
            "password1": <해쉬된 비밀번호>,
            "password2": <해쉬된 비밀번호 확인>
        }
     }
``` 


Log In
-------

Returns access token authorized.

**URI**

/api/v1/account/login 

**Method**

POST

**Request Parameters**

| Key      | Value      | Required |
|:---------|:-----------|:--------:|
| username | *[string]* |    Y     |
| password | *[string]* |    Y     |

**Success Headers**

| Key          | Value       |
|:-------------|:------------|
| Authorization| *[string]*  |


**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
{
    "access_token": <JWT 토큰>
}
``` 


Create Question
-------

Returns question created.

**URI**

/api/v1/question  

**Method**

POST

**Request Parameters**

| Key      | Value      | Required |
|:---------|:-----------|:--------:|
| subject  | *[string]* |    Y     |
| content  | *[string]* |    Y     |


**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 201                   |  CREATED  | 

```json
     {
        "status": <상태메세지>,
        "message": <메세지>,
        "data": {
                    "id":          <질문 id>,
                    "subject":     <질문 제목>,
                    "content":     <질문 내용>,
                    "author_id":   <글쓴이 id>,
                    "vote_count":  <질문 추천 수>,
                    "create_date": <질문 생성일시>,
                    "modify_date": <질문 수정일시>
        }
     }
``` 

Vote Question
-------

Returns question voted.

**URI**

/api/v1/question/vote/*:id*  

**Method**

POST

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
     {
        "status":  <상태메세지>,
        "message": <메세지>,
        "data": {
            "id":          <질문 id>,
            "subject":     <질문 제목>,
            "content":     <질문 내용>,
            "question_id": <질문이 속한 질문 id>,
            "author_id":   <질문 글쓴이의 id>,
            "vote_count":  <질문 추천 수>,
            "create_date": <질문 생성일시>,
            "modify_date": <질문 수정일시>
        }
     }
``` 


Get Questions
-------

Returns all of the questions.

**URI**

/api/v1/question  

**Method**

GET

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
     {
        "status":  <상태메세지>,
        "message": <메세지>,
        "data": [
            {
                "id":          <질문 id>,
                "subject":     <질문 제목>,
                "content":     <질문 내용>,
                "author_id":   <글쓴이 id>,
                "vote_count":  <질문 추천 수>,
                "create_date": <질문 생성일시>,
                "modify_date": <질문 수정일시>
            },
            ...
          ]
     }
``` 


Get Question
-------

Returns the question.

**URI**

/api/v1/question/*:id* 

**Method**

GET

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
     {
        "status":  <상태메세지>,
        "message": <메세지>,
        "data": {
                    "id":          <질문 id>,
                    "subject":     <질문 제목>,
                    "content":     <질문 내용>,
                    "author_id":   <글쓴이 id>,
                    "vote_count":  <질문 추천 수>,
                    "create_date": <질문 생성일시>,
                    "modify_date": <질문 수정일시>
        }
     }
``` 


Modify Question
-------

Returns the question modified.

**URI**

/api/v1/question/*:id* 

**Method**

PUT

**Request Parameters**

| Key      | Value      | Required |
|:---------|:-----------|:--------:|
| subject  | *[string]* |    Y     |
| content  | *[string]* |    Y     |


**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
     {
        "status":  <상태메세지>,
        "message": <메세지>,
        "data": {
                    "id":          <질문 id>,
                    "subject":     <질문 제목>,
                    "content":     <질문 내용>,
                    "author_id":   <글쓴이 id>,
                    "vote_count":  <질문 추천 수>,
                    "create_date": <질문 생성일시>,
                    "modify_date": <질문 수정일시>
        }
     }
``` 


Remove Question
-------

Returns result of whether question is deleted successfully or not in message.

**URI**

/api/v1/question/*:id* 

**Method**

DELETE

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
    {
        "status":  <상태메세지>,
        "message": <삭제성공 여부>,
        "data": null
    }
``` 


Create Answer
-------

Returns answer created.

**URI**

/api/v1/question/*:id*/answer

**Method**

POST

**Request Parameters**

| Key      | Value      | Required |
|:---------|:-----------|:--------:|
| content  | *[string]* |    Y     |

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 201                   |  CREATED  | 

```json
    {
        "status":  <상태메세지>,
        "message": <메세지>,
        "data": {
                    "id":          <질문 id>,
                    "content":     <질문 내용>,
                    "author_id":   <글쓴이 id>,
                    "vote_count":  <질문 추천 수>,
                    "create_date": <질문 생성일시>,
                    "modify_date": <질문 수정일시>
        }
    }
``` 


Vote Answer
-------

Returns answer voted.

**URI**

/api/v1/answer/vote/*:id*  

**Method**

POST

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
     {
       "status":  <상태메세지>,
       "message": <메세지>,
       "data": {
           "id":          <답변 id>,
           "content":     <답변 내용>,
           "question_id": <답변이 속한 질문 id>,
           "author_id":   <답변 글쓴이의 id>,
           "vote_count":  <답변 추천 수>,
           "create_date": <답변 생성일시>,
           "modify_date": <답변 수정일시>
       }
    }
``` 

Get Answers
-------

Returns all of the answers belongs to the question.

**URI**

/api/v1/question/*:id*/answer  

**Method**

GET

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
    {
        "status":  <상태메세지>,
        "message": <메세지>,
        "data": [
            {
                "id":          <질문 id>,
                "content":     <답변 내용>,
                "author_id":   <글쓴이 id>,
                "vote_count":  <질문 추천 수>,
                "create_date": <질문 생성일시>,
                "modify_date": <질문 수정일시>
            },
            ...
          ]
    }
``` 

Modify Answer
-------

Returns answer modified.

**URI**

/api/v1/question/*:question_id*/answer/*:answer_id* 

**Method**

POST

**Request Parameters**

| Key      | Value      | Required |
|:---------|:-----------|:--------:|
| content  | *[string]* |    Y     |

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  CREATED  | 

```json
    {
        "status":  <상태메세지>,
        "message": <메세지>,
        "data": {
                    "id":          <질문 id>,
                    "content":     <답변 내용>,
                    "author_id":   <글쓴이 id>,
                    "vote_count":  <질문 추천 수>,
                    "create_date": <질문 생성일시>,
                    "modify_date": <질문 수정일시>
        }
    }
``` 


Remove Answer
-------

Returns result of whether answer is deleted successfully or not in message.

**URI**

/api/v1/question/*:question_id*/answer/*:answer_id*

**Method**

DELETE

**Success Responses**

| code                  | Status    |
|:----------------------|:----------|
| 200                   |  OK       | 

```json
    {
        "status":  <상태메세지>,
        "message": <삭제성공 여부>,
        "data": null
    }
``` 
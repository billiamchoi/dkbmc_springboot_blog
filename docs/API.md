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
        "status":  "<status message>",
        "message": "<message>",
        "data": {
            "id":               "<member id>",
            "username":         "<username>",
            "email":            "<email>",
            "password":         "<password>",
            "password_confirm": "<password_confirm>"
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
        "access_token": "<JWT Token>"
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
        "status": "<status message>",
        "message": "<message>",
        "data": {
            "id":          "<question id>",
            "subject":     "<question subject>",
            "content":     "<question content>",
            "author_id":   "<author id>",
            "vote_count":  "<number of question vote>",
            "create_date": "<created date of question>",
            "modify_date": "<modified date of question>"
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
        "status":  "<status message>",
        "message": "<message>",
        "data": {
            "id":          "<question id>",
            "subject":     "<question subject>",
            "content":     "<question content>",
            "author_id":   "<author id>",
            "vote_count":  "<number of question vote>",
            "create_date": "<created date of question>",
            "modify_date": "<modified date of question>"
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
        "status":  "<status message>",
        "message": "<message>",
        "data": [
            {
                "id":          "<question id>",
                "subject":     "<question subject>",
                "content":     "<question content>",
                "author_id":   "<author id>",
                "vote_count":  "<number of question vote>",
                "create_date": "<created date of question>",
                "modify_date": "<modified date of question>"
            },
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
        "status":  "<status message>",
        "message": "<message>",
        "data": {
            "id":          "<question id>",
            "subject":     "<question subject>",
            "content":     "<question content>",
            "author_id":   "<author id>",
            "vote_count":  "<number of question vote>",
            "create_date": "<created date of question>",
            "modify_date": "<modified date of question>"
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
        "status":  "<status message>",
        "message": "<message>",
        "data": {
            "id":          "<question id>",
            "subject":     "<question subject>",
            "content":     "<question content>",
            "author_id":   "<author id>",
            "vote_count":  "<number of question vote>",
            "create_date": "<created date of question>",
            "modify_date": "<modified date of question>"
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
        "status":  "<status message>",
        "message": "<success or fail>",
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
        "status":  "<status message>",
        "message": "<message>",
        "data": {
            "id":          "<answer id>",
            "content":     "<answer content>",
            "question_id": "<question id to which the answer belongs>",
            "author_id":   "<author id>",
            "vote_count":  "<number of answer vote>",
            "create_date": "<created date of answer>",
            "modify_date": "<modified date of answer>"
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
        "status":  "<status message>",
        "message": "<message>",
        "data": {
            "id":          "<answer id>",
            "content":     "<answer content>",
            "question_id": "<question id to which the answer belongs>",
            "author_id":   "<author id>",
            "vote_count":  "<number of answer vote>",
            "create_date": "<created date of answer>",
            "modify_date": "<modified date of answer>"
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
        "status":  "<status message>",
        "message": "<message>",
        "data": [
            {
                "id":          "<answer id>",
                "content":     "<answer content>",
                "author_id":   "<author id>",
                "question_id": "<question id to which the answer belongs>",                
                "vote_count":  "<number of answer vote>",
                "create_date": "<created date of answer>",
                "modify_date": "<modified date of answer>"
            },
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
        "status":  "<status message>",
        "message": "<message>",
        "data": {
            "id":          "<answer id>",
            "content":     "<answer content>",
            "author_id":   "<author id>",
            "question_id": "<question id to which the answer belongs>",
            "vote_count":  "<number of answer vote>",
            "create_date": "<created date of answer>",
            "modify_date": "<modified date of answer>"
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
        "status":  "<status message>",
        "message": "<success or fail>",
        "data": null
    }
``` 
<!-- LOGO -->
<h1 align='center'> 42서울 카뎃을 위한 사물함 대여 서비스 </h1>
<br />
<img align="right" src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F3a3b84d9-30c1-4749-b815-363c01bd866b%2Fread_me.gif?table=block&spaceId=1dc14d02-9fef-47d5-828a-c667c7d13337&id=ac3fe35e-1d89-492b-b014-e7b580b60c61&userId=c35a0a1a-c458-4454-a8ac-47aecc0173e3&cache=v2" width="400" height="750">

<h1>
<p align="center">
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F422200c6-d33e-40bd-808c-c7794c4c464e%2Flogo_transparent.png?table=block&id=d119c875-6838-4545-8f74-514f583cdd6e&spaceId=1dc14d02-9fef-47d5-828a-c667c7d13337&width=1740&userId=c35a0a1a-c458-4454-a8ac-47aecc0173e3&cache=v2" alt="Logo" width="300" height="300">
</h1>

## 📇 List

#### -  프로젝트 구성원 [까비들](#ccabi)
#### -  프로젝트 구조에 대한 설명 [프로젝트 구조도](#directory)
#### -  [Git Convention](#git-convention)
#### -  [Technical Stack](#technical-stack)
#### -  [회고록](#note)  
<br/>
<br/>
<br/>

***

## 👨‍👩‍👧‍👧 CCABI

| ```hyoon``` 윤현지 | ```hyospark``` 박효성 | ```skim``` 김수빈 | ```spark``` 박성현 |
| :-: | :-: | :-: | :-: |
| <img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F4bc655ab-7aba-432e-a6ea-235e90f0ccaf%2FUntitled.jpeg?table=block&id=88bd03cc-f0d1-455f-847a-f06d47272665&spaceId=1dc14d02-9fef-47d5-828a-c667c7d13337&width=1740&userId=c35a0a1a-c458-4454-a8ac-47aecc0173e3&cache=v2" width="250"> | <img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc1a166bc-8d97-4bf7-9925-4b18991078ed%2FUntitled.jpeg?table=block&id=3e73475e-e9fe-4bb8-9e02-e44e9e1075c6&spaceId=1dc14d02-9fef-47d5-828a-c667c7d13337&width=1740&userId=c35a0a1a-c458-4454-a8ac-47aecc0173e3&cache=v2" width="250"> | <img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fa42d0fab-9ae8-48a1-bbed-a7d418a0283c%2FUntitled.jpeg?table=block&id=77e2ef75-fec7-4f1b-acb7-5b63eb2f4bf9&spaceId=1dc14d02-9fef-47d5-828a-c667c7d13337&width=1740&userId=c35a0a1a-c458-4454-a8ac-47aecc0173e3&cache=v2" width="250"> | <img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fa552570b-3630-4232-917c-2c3e6c5ad02a%2FUntitled.jpeg?table=block&id=0519db7d-16b6-4a1c-b6f4-eea4e5722e97&spaceId=1dc14d02-9fef-47d5-828a-c667c7d13337&width=1740&userId=c35a0a1a-c458-4454-a8ac-47aecc0173e3&cache=v2" width="250"> |
| [@hyoon](https://github.com/kamg2218) | [@hyospark](https://github.com/kyoshong) | [@skim](https://github.com/subin195-09) | [@spark](https://github.com/Hyunja27) |
| 함께해서 너무x100 즐거웠고 <div></div> 멋진 개발자가 되고싶어졌다..✨  | 함께해서 너무x100 즐거웠고 <div></div> 멋진 개발자가 되고싶어졌다..✨ | 함께해서 너무x100 즐거웠고 <div></div> 멋진 개발자가 되고싶어졌다..✨  | 함께해서 너무x100 즐거웠고 <div></div> 멋진 개발자가 되고싶어졌다..✨ |
<br/>

## 📁 Directory

### frontend
```
├── index.html
├── src
│    ├── component
│    ├── modal
│    ├── pages
│    ├── App.tsx
│    └── main.tsx
└── img
```

### backend
```
├── app.ts
├── bin
│    └── www.ts
├── models
│    ├── db.ts
│    ├── query.ts
│    └── user.ts
├── controllers
│    ├── middleware
│    │    ├── auth.ts
│    │    └── passport.ts
│    └── routers
│         ├── route.ts
│         └── user.ts
├── views
│    ├── index.html
│    ├── assets
│    └── img
├── api
│    ├── openapi.yaml
│    ├── path.yaml
│    └── swagger.yaml
└── .env
```

## 🐈 Git Convention

## 🧑‍💻 Technical Stack

<div align=center> 
  <img src="https://img.shields.io/badge/typescript-3178C6?style=for-the-badge&logo=typescript&logoColor=white">
  <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=white"> 
  <img src="https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=Node.js&logoColor=white"> 
  <img src="https://img.shields.io/badge/tsnode-3178C6?style=for-the-badge&logo=ts-node&logoColor=white"> 
  <br/>
  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=Bootstrap&logoColor=white"> 
  <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white"> 
  <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white"> 
</div>

## 🗒️ NOTE

### 42cabi를 만들어 가면서 본인이 맡은 기능과 어려웠던 부분, 극복해나가는 과정에 대해 알려주세요!
[회고록](https://www.notion.so/hyunja/247ee4f6b0414c2ab9c01e09e2541675)

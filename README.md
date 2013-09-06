newone
======

碩論<智慧型情境感知待辦清單管理系統之研究>的source code
此份source code 主要是在Android上寫一個待辦事項的管理程式，
取得使用者輸入後會回傳給後端server進行「語意推理」，
並將推理出來的待辦事項之所需地點類型，定期利用google API來進行搜尋，
以及配合幾個參數(如Deadline和Priority)進行綜合評分，並提醒使用者分數最高的前三項待辦事項
當提醒的情境是有關於靠近某個可完成待辦事項的地點時，點選提醒會跳出地圖顯示資訊。




the json package is from http://json.org/java/

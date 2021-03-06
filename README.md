# Something
# Android ToDo Application
<img src="./readme/Graphic.png" alt="jetpack" width="60%">

https://play.google.com/store/apps/details?id=com.taetae98.something


## ๐ Introduction
* #### ToDoFragment
* #### RecyclerView๋ฅผ ์ฌ์ฉํ์ฌ List ํ์์ผ๋ก ToDo๋ฅผ ์ฝ๊ฒ ํ์ธํ  ์ ์์ต๋๋ค.
* #### ItemTouchHelper๋ฅผ ์ฌ์ฉํ์ฌ ToDo๋ฅผ ์ ์์ณ๋ก ์ง์ธ ์ ์์ต๋๋ค.
* #### You can use RecyclerView to easily check ToDo in List format.
* #### You can use ItemTouchHelper to clear ToDo with a manual.
<img src="./readme/ToDoFragment1.png" alt="ToDoFragment1" width="30%"><img src="./readme/ToDoFragment2.png" alt="ToDoFragment2" width="30%"><img src="./readme/ToDoFragment3.png" alt="ToDoFragment3" width="30%">
<img src="./readme/ToDoFragment4.png" alt="ToDoFragment4" width="30%"><img src="./readme/ToDoFragment5.png" alt="ToDoFragment5" width="30%">


* #### CalendarFragment
* #### ์ง์  ๋ง๋  CalendarView๋ฅผ ์ฌ์ฉํ์ฌ ToDo๋ฅผ ํ ๋์ ํ์ธํ  ์ ์์ต๋๋ค.
* #### You can view ToDo at a glance using your own CalendarView.
<img src="./readme/CalendarFragment1.png" alt="CalendarFragment1" width="30%"><img src="./readme/CalendarFragment2.png" alt="CalendarFragment2" width="30%">

* #### DrawerFragment
* #### ์๋ ๊ธฐ๋ฅ์ ํตํด ToDo๋ฅผ ์ฃผ์ ๋ณ๋ก ๋ถ๋ฅํ๊ณ  ํ์ธํ  ์ ์์ต๋๋ค.
* #### ๋ฏผ๊ฐํ ์ ๋ณด๋ ์ ๊ธ ๊ธฐ๋ฅ์ ์ฌ์ฉํ์ฌ ๋ณดํธํ  ์ ์์ต๋๋ค.
* #### Drawer function allows you to categorize and check ToDo by topic.
* #### Sensitive information can be protected using the lock function.
<img src="./readme/DrawerFragment1.png" alt="DrawerFragment1" width="30%"><img src="./readme/DrawerFragment2.png" alt="DrawerFragment2" width="30%">
<img src="./readme/DrawerFragment3.png" alt="DrawerFragment3" width="30%"><img src="./readme/DrawerFragment4.png" alt="DrawerFragment4" width="30%">

* #### FinishedFragment
* #### ์๋ฃ๋ ToDo๋ฅผ ํ์ธํ  ์ ์์ต๋๋ค.
* #### You can check the ToDo completed.
<img src="./readme/FinishedFragment.png" alt="FinishedFragment" width="30%">

* #### SettingFragment
* #### ์ฑ์ ๋ํ ์ค์ ์ ํ  ์ ์์ต๋๋ค.
* #### You can set up settings for the app.
<img src="./readme/SettingFragment1.png" alt="SettingFragment1" width="30%"><img src="./readme/SettingFragment2.png" alt="SettingFragment2" width="30%"><img src="./readme/SettingFragment3.png" alt="SettingFragment3" width="30%">

* #### BackupFragment
* #### Firebase์ ์ฐ๋ํ์ฌ ๋ฐฑ์์ ์งํํ  ์ ์์ต๋๋ค.
* #### You can proceed with the backup in conjunction with Firebase.
<img src="./readme/BackupFragment1.png" alt="BackupFragment1" width="30%"><img src="./readme/BackupFragment2.png" alt="BackupFragment2" width="30%"><img src="./readme/BackupFragment3.png" alt="BackupFragment3" width="30%">

* #### ETC
* #### Notification ๊ธฐ๋ฅ์ ์ฌ์ฉํ์ฌ ์๋ฆผ์ฐฝ์ ToDo๋ฅผ ํ์ํ  ์ ์๋ค.
* #### Sticky ๊ธฐ๋ฅ์ ์ฌ์ฉํ์ฌ ํธ๋ํฐ ์ ๊ธํด์ ์ ๋ฐ๋ก ์ฑ์ ์คํ์ํฌ ์ ์๋ค.
* #### You can use the Notification function to display ToDo in the notification window.
* #### The Sticky function allows you to launch the app immediately after unlocking your phone.
<img src="./readme/Notification.png" alt="Notification" width="30%"><img src="./readme/Sticky.png" alt="Sticky" width="30%"><img src="./readme/BackupFragment3.png" alt="BackupFragment3" width="30%">

## ๐ Android Jetpack
<img src="./readme/jetpack.png" alt="jetpack" width="60%">

* ### Room : SQLite๋ฅผ ๋์ฒดํ์ฌ Android์ Local Database๋ฅผ ์ฝ๊ฒ ๊ตฌ์ถํ  ์ ์๋ ๋ผ์ด๋ธ๋ฌ๋ฆฌ. ToDo์ Drawer๋ฅผ ๊ด๋ฆฌํ  ์ ์๋ค.
* ### ViewModel & LiveData : ViewModel๊ณผ LiveData๋ฅผ ์ฐ๋ํ์ฌ UI Controller์ ๋ฐ์ดํฐ๋ฅผ ๋ถ๋ฆฌํ์ฌ ๊ด๋ฆฌํ  ์ ์๊ณ , DataBinding์ ์ฌ์ฉํ์ฌ UI๋ฅผ ์ฝ๊ฒ ๊ตฌ์ถํ  ์ ์๋ค.
* ### Navigation : Activity์ ์์ฑ, Stack๊ด๋ฆฌ ๋ฑ์ ๋ํ ๋ถ๋ด์ Fragment๋ฅผ ์ฌ์ฉํด ์ค์ผ ์ ์๊ณ , ์๋๋ฉ์ด์๊ณผ SafeArgs ๋ฑ ํธ๋ฆฌํ๊ฒ UI๋ฅผ ๊ตฌ์ฑํ  ์ ์๋ค.
* ### Hilt : Android์ DI ํจํด์ ์ ์ฉํ์ฌ Manager, Repository ๋ฑ ์ ์ญ์์ ์ฌ์ฉํ๋ ๊ธฐ๋ฅ๋ค์ ์ฝ๊ฒ ๊ด๋ฆฌํ  ์ ์๋ค.
* ### DataStore : SharedPreferences๋ฅผ ๋์ฒดํ  ์ ์๊ณ  Key : Value ํ์์ผ๋ก ๊ฐ์ ์ ์ฅํ์ฌ ์ค์  ๊ฐ์ ๊ด๋ฆฌํ  ์ ์๋ค.
* ### Room : A library that can easily be deployed on Android to replace SQLite. I can manage ToDo and Drawer.
* ### ViewModel & LiveData : ViewModel๊ณผ LiveData๋ฅผ ์ฐ๋ํ์ฌ UI Controller์ ๋ฐ์ดํฐ๋ฅผ ๋ถ๋ฆฌํ์ฌ ๊ด๋ฆฌํ  ์ ์๊ณ , DataBinding์ ์ฌ์ฉํ์ฌ UI๋ฅผ ์ฝ๊ฒ ๊ตฌ์ถํ  ์ ์๋ค.
* ### Navigation : The burden of generating activity and managing stacks can be reduced using Fragment, and UI can be conveniently configured such as animation and SafeArgs.
* ### Hilt : Applying DI patterns to Android makes it easy to manage functions used globally such as Manager, Repository, etc.
* ### DataStore : You can replace Shared Preferences and manage the set value by saving the value in Key: Value format.

## โก Features
* ToDo : ToDo ๋ชฉ๋ก์ List ํ์, Calendar ํ์์ผ๋ก ์ฝ๊ฒ ํ์ธํ  ์ ์๊ณ , ์ ์ค์ฒ๋ฅผ ์ฌ์ฉํ์ฌ ์ฝ๊ฒ ์ฌ์ฉํ  ์ ์๋ค.
* Drawer : ToDo๋ฅผ ์ฃผ์ ๋ณ๋ก ๋ถ๋ฅํ์ฌ ๊ด๋ฆฌํ  ์ ์๋ค.
* Sticky Mode : ํธ๋ํฐ ์ ๊ธํ๋ฉด์ ์ฑ์ ๋ฐ๋ก ์คํ์ํค๋ ๊ธฐ๋ฅ์ผ๋ก ToDo๋ฅผ ๊น๋นกํ์ง ์๊ณ  ํ์ธํ  ์ ์๋ค.
* Notification : ํธ๋ํฐ ์๋ฆผ์ฐฝ์ ToDo๋ฅผ ํ์ํ์ฌ ์ฝ๊ฒ ํ์ธํ  ์ ์๋ค.
* Security : Drawer์ ์ ๊ธ ๊ธฐ๋ฅ์ ์ฌ์ฉํ์ฌ ๋ฏผ๊ฐํ ToDo๋ฅผ ๊ด๋ฆฌํ  ์ ์๋ค.
* Backup : Firebase์ Google Account๋ฅผ ํตํด ๋ฐฑ์/๋ณต์์ ์งํํ  ์ ์๋ค.
* DarkMode : ๋คํฌ๋ชจ๋๋ฅผ ์ง์ํฉ๋๋ค.
* ToDo : The ToDo list can be easily checked in List format and Calendar format, and can be used easily using gestures.
* Drawer : ToDo can be categorized by subject and managed.
* Sticky Mode: You can check ToDo without forgetting to run the app immediately when you lock your phone.
* Notification : You can easily check it by displaying ToDo in the mobile phone notification window.
* Security : Lock on Drawer to manage sensitive ToDo.
* Backup : Firebase and Google Account allow backup/restore.
* DarkMode :Supports dark mode.
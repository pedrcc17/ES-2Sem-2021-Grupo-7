Project developed by group 7 for the class Engenharia de Software, from 3rd year of Licenciatura de Engenharia Informática in ISCTE-IUL. 2020/2021

The app requires some precedures in order to work:

In the early stages of the project some libraries were adopted in the development of this application, such has Apache POI (Java API for Microsoft Documents) and miglayout to extend Window Builder's capabilities.

In order to add these libraries to run the app, you'll need to add these to your own Classpath (these are included in ExcelAPI folder).

Furthermore, the library JUnit5 was also used in testing our code resemblance with the requirements.

To read your project on this app properly, you'll have to follow the next rules:

1. During the creation of a method, please use only one line to write the header of the method, together with the attributes.
2. Please don't use the word "class" within any attribute name.
3. For a better reading, please delete your commentaries and blank lines from inside the code.
4. Please try to leave your code cleaned and organized as possible.

To run this app without errors, this are the steps you'll need:

1. Open the app
2. Go to the menu 'Rules' and either 'Create a New Rule' or 'Change Rules'. The app won't load your code properly without any rule applied. How to apply a rule:
	a) If you choose 'Create a New Rule':
		A window will appear, and you'll have to enter the new rule. After that, press 'Save / Set Rule' and that's the rule that'll be applied to your project.
	b)If you choose 'Change Rules':
		If you already have any rules created, you'll see a list with all of them. You can pick one and either change it or leave it that way.
		After you select the rule you want to apply, press 'Update / Set Rule' and that's the rule that'll be applied to your project.
3. To create a Metrics excel file with the rule you want, after selecting the rule, go to the menu 'File' and select 'Project to Metrics File'.
	Choose the folder (project) you want and choose a name for the Excel File.
	The Metrics excel file will get created.
4. To see the metrics, go to the menu 'File' and select 'Open Metrics'.
	Select the Excel File with the metrics you want. The only Excel Files that can be opened are the ones created with this app.
	You'll be able to see all the packages, classes and methods on the TreeView, as well as the metrics for each.
5. To test if the rules are good, you need to have a Metrics Excel File opened through 'Open Metrics' (see step 4).
	After you open the file, press the button 'Test Results'. This will compare the results from the Metrics created on step 3 with the metrics wrote by the specialists (Excel provided by the teachers).
	
Please follow all these steps so you don't find any problem.
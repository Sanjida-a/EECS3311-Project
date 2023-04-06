# EECS3311-Group8-PharmacyManagementSystem
ITERATION 3
Please access the Wiki for all important details. Links to the UML Class Diagram and System Architecture are also included in the Wiki: https://github.com/jewbe22/EECS3311-project-group8/wiki

Other required documentation such as the UpdatedPlan, Updated Big Stories, Detailed Stories, Addressing Issues (by QA team), Major Changes Made In Iteration 3, QA Activity Report, Refactoring Document, Retrospective Activity, Peer Evaluation form and log.txt (called logIteration3.txt) can be found in the "Iteration3 - Documentation" folder.

To execute the program, ensure you have the latest version of Java. Download the Iteration2ExecutableDemo.jar file and run it from your system (you can simply double-click on it).

To see the files that contain java code, please first click on the "York and Co. Pharmacy Management System" folder and then the "src" folder.

(For the TAs): To run the Integration and Unit Tests without any exceptions, please change the password on EACH test file to your local SQL password (and do not touch the "TEST (DO NOT TOUCH" medication with ID = 7 in the inventory).

[OLD - IGNORE BELOW]
Iteration 2:

Please access the Wiki for all important details. Links to the UML Class Diagram and System Architecture are also included in the Wiki: https://github.com/jewbe22/EECS3311-project-group8/wiki

Other documentation such as the UpdatedPlan, Peer Evaluation form and log.txt (called logIteration2.txt) can be found in the "Iteration2 - Documentation" folder.

To execute the program, ensure you have the latest version of Java. Download the Iteration2ExecutableDemo.jar file and run it from your system (you can simply double-click on it).

To see the files that contain java code, please first click on the "York and Co. Pharmacy Management System" folder and then the "src" folder.

(For the TAs): To run the Integration and Unit Tests without any exceptions, please change the password on EACH test file to your local SQL password.

-------------------------------------------------------------------------------------------
Bugs/Limitations for Iteration2

-Patient Healthcard# : Please enter a number that is less than 2147483647

-Patient Date of Birth: Please enter a number that is less than 2147483647

-Patient Phone#: Please enter a number that is less than 2147483647

Any number greater than these numbers will produce an error message of 'invalid output'. This will be fixed in iteration 3.

-For Add order, allow quantity bought to happen at same time as adding prescription. But need to confirm quantity bought <= number of prescribed refills.

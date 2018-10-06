# Manage
This application is mainly for the retailers or shopkeepers who buy the stuffs in large amount. 
It helps to keep the track of the stuffs bought and also calculates the total amount that it will cost.
The application comes with a very simple interface with a Floating Action Button to add the record of the item.

For storing the records, the application uses the MySQL database with the addition of the Content Provider to add the another layer to it.
Instead of the recycler view, list view is use as its easier to work with the assosication of the Cursor Adapter.

Note: In order to edit or delete any record at first we need to click the record in order to record its id and then we can swipe and carry out the action.

P.S : If the application is used carefully it's really great but yet it has an issue. Suppose we have two records and we clicked the first record.
The id of first record is saved so if we swipe the second record and click the delete action, the first record will be deleted.

Feel free to use the code and please provide the feedback.

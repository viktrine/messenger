# messenger

This is a RESTful messenger web service.

It gives the functionality of creating a message, adding comments to a message
and getting the replies of a comment.

The uniqness is achived by use of a messageId, commentId and the relpyId. 

The system uses an in memory database, not connecting to an external database. 
It is powered by GlassFish server and tests done by use of RestClient firefox
add-on client.

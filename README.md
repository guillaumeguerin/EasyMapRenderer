# EasyMapRenderer

Renders OpenStreet Maps .osm files to an image.

![Preview](https://raw.githubusercontent.com/guillaumeguerin/EasyMapRenderer/master/preview.jpg "Preview")

## Output customization

The output can be customized through json files. Below is an example using the default styles avalaibles :

![default style](https://raw.githubusercontent.com/guillaumeguerin/EasyMapRenderer/master/style1.jpg "Style 1")
![black and white style](https://raw.githubusercontent.com/guillaumeguerin/EasyMapRenderer/master/style2.jpg "Style 2")
![dark style](https://raw.githubusercontent.com/guillaumeguerin/EasyMapRenderer/master/style3.jpg "Style 3")
![light style](https://raw.githubusercontent.com/guillaumeguerin/EasyMapRenderer/master/style4.jpg "Style 4")


## Gallery

Here are examples rendered with this application.

![Farm](https://raw.githubusercontent.com/guillaumeguerin/EasyMapRenderer/master/output1.png "Farm")


## Database

The SQLLite database the application uses can be opened with any tools made for SQLLite (i.e https://sqlitebrowser.org/)

You can use it to modify your database and optimize your render time.

For example if the want to keep informations only related to rivers and lakes we can use the following SQL Query :

```SQL
DELETE FROM TAG WHERE TYPE2 NOT IN ('lake','river');

DELETE
FROM RELATION
WHERE ID NOT IN (
	SELECT USED_BY FROM TAG WHERE TYPE2 IN ('lake','river')
);

DELETE
FROM WAY
WHERE ID NOT IN (
	SELECT USED_BY FROM TAG WHERE TYPE2 IN ('lake','river')
);

DELETE
FROM NODE
WHERE ID NOT IN (
	SELECT ID FROM WAY
);
```


## Server

Go to http://localhost:8080/ to see the tile server !

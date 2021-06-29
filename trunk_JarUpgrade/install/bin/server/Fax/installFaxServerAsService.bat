rem Dotnet Framework Installation path will be updated dynamically during the Fax server installation
Dotnet Framework Installation path
rem The below line won't affect even if output serive not available.
sc config "McKFaxoutSvc" depend= "McKOutputSvc"
<?php
	class Database{
    /*
     * USAGE OF THIS CLASS
     * 
     */
    public $connection;


    public static function Instance()
    {
        static $inst = null;
        if ($inst === null) {
            $inst = new Database();
        }
        return $inst;
    }

    private function __construct()
    {
        $this->connection = $this->connect();
    }

    public function connect() {
        $db = "hydrosaver";
        $host = "us-cdbr-azure-west-b.cleardb.com";
        $user = "b1288fc6f3969a";
        $pass = "4718fb0b";
        return new PDO(
            "mysql:host=$host;dbname=$db",
            $user,
            $pass,
            array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION, PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8")
        );
    }

}
?>
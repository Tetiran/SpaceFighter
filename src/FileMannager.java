import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

class FileMannager {


     private static final String SAVE_FILE = "files/gamesave.txt";
    static boolean loadSave(){

        File tmpDir = new File(SAVE_FILE);
        boolean exists = tmpDir.exists();
        if(exists){
            int time;
            int score;
            LinkedList<Entity> entites=GameField.getEntites();
            try {
                BufferedReader in = new BufferedReader(new FileReader(SAVE_FILE));
                try {
                    GameField.setGameTime(Integer.parseInt(in.readLine()));
                    GameField.setScore(Integer.parseInt(in.readLine()));
                    String line=in.readLine();
                    while (line!=null){
                        String Entity=line;
                        if (Entity.equals("EnemyShip")){
                            EnemyShip eship;
                            Double posx, posy;
                            int Health, Shield, Armor, Speed;
                            posx=Double.parseDouble(in.readLine());
                            posy=Double.parseDouble(in.readLine());
                            Health=Integer.parseInt(in.readLine());
                            Shield=Integer.parseInt(in.readLine());
                            Armor=Integer.parseInt(in.readLine());
                            Speed=Integer.parseInt(in.readLine());
                            eship=new EnemyShip(posx, posy,32,32,
                                    Health, Armor,Shield,Speed);
                            GameField.addEntity(eship);

                        } else if (Entity.equals("PlayerShip")){
                            PlayerShip eship;
                            Double posx, posy;
                            int Health, Shield, Armor, Speed;
                            posx=Double.parseDouble(in.readLine());
                            posy=Double.parseDouble(in.readLine());
                            Health=Integer.parseInt(in.readLine());
                            Shield=Integer.parseInt(in.readLine());
                            Armor=Integer.parseInt(in.readLine());
                            Speed=Integer.parseInt(in.readLine());
                            eship=new PlayerShip(posx, posy,32,32,
                                    Health, Armor,Shield,Speed);
                            GameField.setPlayer(eship);
                        } else if(Entity.equals("Laser")){
                            Laser laser;
                            double posx, posy, angle;
                            boolean owner;
                            posx=Double.parseDouble(in.readLine());
                            posy=Double.parseDouble(in.readLine());
                            angle=Double.parseDouble(in.readLine());
                            owner=Boolean.parseBoolean(in.readLine());
                            laser=new Laser(posx,posy,32,32,10,
                                    20,angle,owner);
                            GameField.addEntity(laser);


                        }
                        line=in.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //loadsome shit

            //new EnemyShip()
        }

        return exists;


    }
    static void saveSave(){
        int time = GameField.getGameTime();
        int score=GameField.getScore();
        LinkedList<Entity> entites=GameField.getEntites();

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(SAVE_FILE));
            out.write(Integer.toString(time));
            out.write('\n');
            out.write(Integer.toString(score));
            out.write('\n');
            Iterator<Entity> iter = entites.iterator();
            while (iter.hasNext()) {
                Entity ent =iter.next();
                if(ent instanceof EnemyShip){
                    out.write("EnemyShip");
                    out.write('\n');
                    out.write(Double.toString(ent.getPosx()));
                    out.write('\n');
                    out.write(Double.toString(ent.getPosy()));
                    out.write('\n');
                    out.write(Integer.toString(((EnemyShip) ent).getHealth()));
                    out.write('\n');
                    out.write(Integer.toString(((EnemyShip) ent).getShield()));
                    out.write('\n');
                    out.write(Integer.toString(((EnemyShip) ent).getArmor()));
                    out.write('\n');
                    out.write(Integer.toString(((EnemyShip) ent).getSpeed()));
                    out.write('\n');


                } else if(ent instanceof Laser){
                    out.write("Laser");
                    out.write('\n');
                    out.write(Double.toString(ent.getPosx()));
                    out.write('\n');
                    out.write(Double.toString(ent.getPosy()));
                    out.write('\n');
                    out.write(Double.toString(((Laser) ent).getAngle()));
                    out.write('\n');
                    out.write(Boolean.toString(((Laser) ent).getOwner()));
                    out.write('\n');
                } else if(ent instanceof PlayerShip){
                    out.write("PlayerShip");
                    out.write('\n');
                    out.write(Double.toString(ent.getPosx()));
                    out.write('\n');
                    out.write(Double.toString(ent.getPosy()));
                    out.write('\n');
                    out.write(Integer.toString(((PlayerShip) ent).getHealth()));
                    out.write('\n');
                    out.write(Integer.toString(((PlayerShip) ent).getShield()));
                    out.write('\n');
                    out.write(Integer.toString(((PlayerShip) ent).getArmor()));
                    out.write('\n');
                    out.write(Integer.toString(((PlayerShip) ent).getSpeed()));
                    out.write('\n');
                }

            }



            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void deleteSave(){
        File file = new File(SAVE_FILE);
        boolean exists = file.exists();
        if(exists) {
            file.delete();
        }
    }

}

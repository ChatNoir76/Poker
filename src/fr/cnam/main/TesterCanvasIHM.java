package fr.cnam.main;

import java.util.*;
import java.awt.*;

import fr.cnam.ihm.*;
import fr.cnam.tore.*;

public class TesterCanvasIHM
{
    /** MÈthode de test de la classe.
        Ce programme crÈe un canvas dans lequel il est possible de dessiner des lignes, des point et des textes.
        Les boutons permettent de tester le canvas.
    */
    public static void main(String... args)
    {
        Test t = new Test();
    }

}

// ===============================================================
/** Classe utilis√©e pour tester 
 */
class Test implements FormulaireInt, ControlesCanvasIHM
{
    private CanvasIHM canvas;
    private int mode;
    private int posxc;
    private int posyc;
    Formulaire form;
    private boolean debutTrace;
    private boolean courantTrace;
    private int numPoint;

    public Test()
    {
        // 
        mode=0;
        debutTrace=false;
        courantTrace=false;

        // Creation d'un formuaire qui contient des boutons et le canvas
        //
        form = new Formulaire("TESTER",this,310+600+30,
                              600+30);
        // -- les boutons
        form.horizontal();
        form.setWidthButtonCour(200);
        form.addButton("DESSINER_LIGNE","Dessiner une ligne");
        form.addButton("EFFACER","Effacer");
        form.addButton("AFFICHER_POLYGONES","Afficher des polygones");
        form.vertical();
        form.dessous(0);
        form.setWidthTextCour(400);
        form.addText("INFO","info",false,"");
        form.dessous(0);

        // -- cr√©ation du canvas
        canvas = form.addCanvasIHM(600,600,this);
        canvas.getPanel().setBackground(Color.yellow);

        // Affichage du formulaire
        form.afficher(50,50);
    }

    // La methode a implementer de l'interface FormualireInt pour le formulaire
    //
    public void  submit(Formulaire form,String nom)
    {
        // Les m√©thodes concernant le canvas que vous pouvez utiliser sont :
        //
        // public void ajouterLigne(int couleur,int x1,int y1,int x2,int y2)
        //
        // public void ajouterTexte(String texte,int x,int y,int couleur)
        //
        // public void ajouterPolygone(Object o,Position p,int couleur,Point... points)
        //
        // public void effacerCanvas()
        //
        // public JPanel getPanel()
        //
        //
        //
        //
        courantTrace=false;

        if (nom.equals("DESSINER_LIGNE")) 
            {
                form.setValeurChamp("INFO","Cliquer plusieurs fois dans le canvas");
                
                mode = 1;
                debutTrace = true;
            }

        if (nom.equals("EFFACER")) 
            {
                canvas.effacerCanvas();
            }

        if (nom.equals("AFFICHER_POLYGONES")) 
            {
                canvas.effacerCanvas();
                Random rdm = new Random();
                
                for(int k=0;k<5;k++)
                    for(int i=0;i<100/(k+1);i++)
                    {
                        Position p = new Position(rdm.nextInt(20),rdm.nextInt(20),k);
                        afficherCube(p,p,k);
                    }
            }
    }

    // action quand on clique dans le canvas
    //
    public void pointerCanvas(Object target,int x,int y,CanvasIHM canvas)
    {
        form.setValeurChamp("INFO","");
        if (debutTrace)
            {
                debutTrace=false;
                courantTrace=true;
                this.posxc = x;
                this.posyc = y;
                this.numPoint = 1;
            }
        else if (courantTrace)
            {
                canvas.ajouterLigne(3,this.posxc,this.posyc,x,y);
                canvas.ajouterTexte("Point "+this.numPoint,this.posxc,this.posyc,6);
                this.numPoint++;
                this.posxc = x;
                this.posyc = y;
            }

        if (target!=null)
            {
                Position pos = (Position)target;
                System.out.println(String.format("[%d %d %d]",
                                                 pos.x,
                                                 pos.y,
                                                 pos.z));
            }
    }

    // Non utilise ici
    //
    public void pointerCaseGrille(int xCase,int yCase,CanvasIHM ihm)
    {
    }

    // Afficher un cube en 3D
    //
    private void afficherCube(Object obj,Position p,int couleur)
    {
        double pzXPourc=0.6;
        double pzYPourc=0.3;
        int pas = 20;
        int pzX = (int)((double)pas*pzXPourc);
        int pzY = (int)((double)pas*pzYPourc);

        int origineX=20+p.z*pzX;
        int origineY=20+p.z*pzY;
        int px=p.x;
        int py=p.y;

        Color col1,col2,col3;

        ArrayList<Color> table = new ArrayList<Color>();
        int[] plage={0,128,255};
        
        table.add(new Color(255,0,0));
        table.add(new Color(0,255,0));
        table.add(new Color(0,0,255));

        table.add(new Color(255,255,0));
        table.add(new Color(0,255,255));
        table.add(new Color(255,0,255));

        table.add(new Color(128,255,0));
        table.add(new Color(0,128,255));
        table.add(new Color(128,0,255));

        table.add(new Color(255,128,0));
        table.add(new Color(0,255,128));
        table.add(new Color(255,0,128));

        table.add(new Color(128,128,0));
        table.add(new Color(0,128,128));
        table.add(new Color(128,0,128));

        table.add(new Color(128,0,0));
        table.add(new Color(0,128,0));
        table.add(new Color(0,0,128));

        if( (couleur<0)||(couleur>=table.size()) )
            col1 = new Color(125,125,125);
        else
            col1 = table.get(couleur);
        
        double[] polaire = polaire(col1.getRed(),col1.getGreen(),col1.getBlue());
        double r2 = polaire[0]*0.66;
        double r3 = polaire[0]*0.44;
        double[] cart2 = cartesienne(r2,polaire[1],polaire[2]);
        double[] cart3 = cartesienne(r3,polaire[1],polaire[2]);
        
        col2 = new Color((int)cart2[0],(int)cart2[1],(int)cart2[2]);
        col3 = new Color((int)cart3[0],(int)cart3[1],(int)cart3[2]);

        // Face de cote gauche
        RessourcesCanvas rc = new RessourcesCanvas();
        rc.setBackground(col3);
        canvas.ajouterPolygone(rc,obj,p,new Point(px*pas+origineX,py*pas+origineY),
                               new Point(px*pas+origineX+pzX,py*pas+origineY+pzY),
                               new Point((px)*pas+origineX+pzX,(py+1)*pas+origineY+pzY),
                               new Point((px)*pas+origineX,(py+1)*pas+origineY));

        //Face du haut
        rc = new RessourcesCanvas();
        rc.setBackground(col1);
        canvas.ajouterPolygone(rc,obj,p,new Point(px*pas+origineX,py*pas+origineY),
                               new Point((px+1)*pas+origineX,py*pas+origineY),
                               new Point((px+1)*pas+origineX+pzX,py*pas+origineY+pzY),
                               new Point((px)*pas+origineX+pzX,(py)*pas+origineY+pzY));
        
        //Face de devant
        rc = new RessourcesCanvas();
        rc.setBackground(col2);
        canvas.ajouterPolygone(rc,obj,p,new Point(px*pas+origineX+pzX,py*pas+origineY+pzY),
                               new Point((px+1)*pas+origineX+pzX,py*pas+origineY+pzY),
                               new Point((px+1)*pas+origineX+pzX,(py+1)*pas+origineY+pzY),
                               new Point((px)*pas+origineX+pzX,(py+1)*pas+origineY+pzY));
                                           
    }

    // Conversion de coord cartesienne en coord polaire dans un
    //   espace 3D
    //
    static double[] polaire(double x,double y,double z)
    {
        double[] pol = new double[3];
        double r=Math.sqrt(x*x+y*y);
        pol[0]=Math.sqrt(r*r+z*z);   // R
        pol[1]=Math.acos(x/r);       // teta
        pol[2]=Math.asin(z/pol[0]);  // phi

        return pol;
    }

    // Conversion de coord polaire en coord cartesienne dans un
    //  espace 3D
    //
    static double[] cartesienne(double R,double teta,double phi)
    {
        double[] cart = new double[3];
        double r = R*Math.cos(phi);
        cart[0]=r*Math.cos(teta);
        cart[1]=r*Math.sin(teta);
        cart[2]=R*Math.sin(phi);
        for(int i=0;i<3;i++)
            {
                if (cart[i]<0) cart[i]=120;
                if (cart[i]>255) cart[i]=120;
            }
    
        return cart;
    }


}

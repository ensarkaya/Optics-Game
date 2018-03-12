public class ConcaveMirror extends SphericalMirrors {
   
   double alpha;
   
   /**
    * This method is the constructor method of ConcaveMirror class
    * @param row is the number of row which holds the mirror
    * @param column is the number of column which holds the mirror
    * @param angle is the angle of mirror with respect to x axis
    */
   public ConcaveMirror(int row, int column, double angle) {
      super(row*60, column*60, angle);
   }

   /**
    * This method is the constructor method of ConcaveMirror class
    * @param locX is the count of X axis which holds the mirror
    * @param locY is the count of Y axis which holds the mirror
    * @param angle is the angle of mirror with respect to x axis
    */  
   public ConcaveMirror(double locX, double locY, double angle) {
      super((int)locX, (int)locY, angle);
   }
   
   /*
    * This method determines how the light is going to reflect from Concave Mirror
    * @ param light is the ligth which is going to reflect from concave mirror
    * 
    */
   @Override
   public Light reflect (Light light)
   {
      // finding the vector from center to the intersection
      Vector centerVect = new Vector(light.endX-getXCenter(), -light.endY+getYCenter());
      Vector lightVect = new Vector(light.vector.x, light.vector.y);
      
      // finding the angle between the coming light and the vector from center to the intersection
      double dotProduct = lightVect.x * centerVect.x + lightVect.y * centerVect.y;
      double magnitudeProduct = lightVect.m() * centerVect.m();
      alpha = Math.toDegrees(Math.acos(dotProduct / magnitudeProduct));
      
      
      if (alpha > 90)
      {
         alpha = 180 - alpha;
      }
      
      double angleDifference = centerVect.angle() - light.vector.angle();
      
      if(angleDifference < 0)
      {
         angleDifference+=360;
      }
      
      else if (angleDifference > 180)
      {
         angleDifference-=360;
      }
      
      if (angleDifference > 180)
      {         
         double angle = -2 * alpha - 180 + light.angle;
         return new Light( light.getEndX(), light.getEndY(), angle, light.color, light.game);         
      }
      
      else if (angleDifference < -180)
      {
         
         double angle =  180 + 2 * alpha + light.angle;
         return new Light( light.getEndX(), light.getEndY(), angle, light.color, light.game);
         
      }
      else
      {
         if (angleDifference <= 0)
         {
            double angle =  -2 * alpha - 180 + light.angle ;
            return new Light( light.getEndX(), light.getEndY(), angle, light.color, light.game);
         }
         else
         {
            double angle = 180 + 2 * alpha + light.angle;
            return new Light( light.getEndX(), light.getEndY(), angle, light.color, light.game);
         }
      }
   }
   
   /*
    * This method determines wheather the light touches or not to the concave mirror
    * 
    * 
    */
   @Override
   public int touches(Light light) {
      
      
      boolean appropriateAngle = false;
      
      double lightCenterAngle = Math.toDegrees(Math.atan2( - light.endy + getYCenter(), light.endx - getXCenter()));
      
      if( lightCenterAngle < 0)
         lightCenterAngle += 360;
      
      
      
      if( angle <= 150 && angle >= 0)
      {
         if(lightCenterAngle > angle + 150 && lightCenterAngle < angle + 210)
            appropriateAngle = true;
         else
            appropriateAngle = false;
      }
      
      else if ( angle <= 210 && angle > 150)
      {
         if(lightCenterAngle >= angle + 150 && lightCenterAngle < 360 || lightCenterAngle >= 0 && lightCenterAngle < angle - 150 )
            appropriateAngle = true;
         else
            appropriateAngle = false;
      }
      
      else
      {
         if(lightCenterAngle > angle - 210 && lightCenterAngle < angle - 150)
            appropriateAngle = true;
         else
            appropriateAngle = false;
      }
      
      
      
      double length = Math.sqrt( Math.abs((light.endy-getYCenter())*(light.endy-getYCenter())+(light.endx - getXCenter())*(light.endx - getXCenter() )));
      
      //System.out.println(length );
      //System.out.println(((int)length*100)/100 + " " + appropriateAngle + " "+ ((int)lightCenterAngle*100)/100.0);
      
      //System.out.println(angle);
      //System.out.println(appropriateAngle);
      if ( length <= CENTER + TOLERANCE && length >= CENTER - TOLERANCE && appropriateAngle )
      {
         if ( length >= CENTER )
         {
            return 0;
         }
         else
         {
            //System.out.println("New Light" );
            //System.out.println();
            return 1;
         }
      }
      
      return -1;
   }
   
   public void rotate (double newAngle)
   {
      angle = newAngle;
      if (angle < 0)
         angle = angle % 360 + 360;
      else 
         angle = angle % 360;
   }
   
   // Aynan�n Merkezinin X koordinat�
   public double getXCenter()
   {
      return getCenterX() + CENTER * Math.cos( Math.toRadians(angle) );
   }
   
   
   // Aynan�n Merkezinin Y koordinat�
   public double getYCenter()
   {
      return getCenterY() - CENTER * Math.sin( Math.toRadians(angle) );
   }
   
   
   @Override
   public String getImageName() {
      // TODO Auto-generated method stub
      return "concave mirror.png";
   }
   
   
}

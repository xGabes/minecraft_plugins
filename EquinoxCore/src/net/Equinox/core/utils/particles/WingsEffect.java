package net.Equinox.core.utils.particles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class WingsEffect
{
	
	static boolean x = true;
    static boolean o = false;

	private static boolean[][] _shape = {
            {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
            {o, o, o, x, o, o, o, o, o, o, o, o, x, o, o, o},
            {o, o, x, x, o, o, o, o, o, o, o, o, x, x, o, o},
            {o, x, x, x, x, o, o, o, o, o, o, x, x, x, x, o},
            {o, x, x, x, x, o, o, o, o, o, o, x, x, x, x, o},
            {o, o, x, x, x, x, o, o, o, o, x, x, x, x, o, o},
            {o, o, o, x, x, x, x, o, o, x, x, x, x, o, o, o},
            {o, o, o, o, x, x, x, x, x, x, x, x, o, o, o, o},
            {o, o, o, o, o, x, x, x, x, x, x, o, o, o, o, o},
            {o, o, o, o, o, o, x, x, x, x, o, o, o, o, o, o},
            {o, o, o, o, o, x, x, o, o, x, x, o, o, o, o, o},
            {o, o, o, o, x, x, x, o, o, x, x, x, o, o, o, o},
            {o, o, o, o, x, x, o, o, o, o, x, x, o, o, o, o},
            {o, o, o, o, x, o, o, o, o, o, o, x, o, o, o, o},
            {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
    };
	
	public static void drawParticles(Location location, Particle particleType) {
        double space = 0.20;
        double defX = location.getX() - (space * _shape[0].length / 2) + space;
        //double defX = location.getX();
        double x = defX;
        double y = location.clone().getY() + 2.8;
        double z = location.clone().getZ();
        double fire = -((location.getYaw() + 180) / 60);
        fire += (location.getYaw() < -180 ? 3.25 : 2.985);

        for (int i = 0; i < _shape.length; i++) {
            for (int j = 0; j < _shape[i].length; j++) {
                if (_shape[i][j]) {

                    Location target = location.clone();
                    target.setX(x);
                    target.setY(y);
                    target.setZ(z);

                    Vector v = target.toVector().subtract(location.toVector());
                    Vector v2 = getBackVector(location);
                    v = rotateAroundAxisY(v, fire);
                    v2.setY(0).multiply(-0.5);

                    location.add(v);
                    location.add(v2);
                    for (int k = 0; k < 3; k++)
                    {
                      	location.getWorld().spawnParticle(particleType, location, 1, 0D, 0D, 0D, 0D);
                    	
                    }
                      //  location.getWorld().spawnParticle(particleType, location, 10);
                      //  location.getWorld().playEffect(location, Effect.FLAME, 10);
                    location.subtract(v2);
                    location.subtract(v);
                }
                x += space;
            }
            y -= space;
            //z -= 0.05f;
            x = defX;
        }
    }
	
	public static Vector rotateAroundAxisY(Vector v, double fire) {
        double x, z, cos, sin;
        cos = Math.cos(fire);
        sin = Math.sin(fire);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static Vector getBackVector(Location loc) {
        final float newZ = (float) (loc.getZ() + (1 * Math.sin(Math.toRadians(loc.getYaw() + 90 * 1))));
        final float newX = (float) (loc.getX() + (1 * Math.cos(Math.toRadians(loc.getYaw() + 90 * 1))));
        return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
    }
}

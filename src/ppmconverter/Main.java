/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppmconverter;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;

/**
 *
 * @author João Hudson
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        if(args.length < 2)
        {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
        
        try{
            File in = new File(args[0]);
            File out = new File(args[1]);
            Main converter = new Main();
            float[][] data = converter.readPPM(in);
            BufferedImage image = converter.makeImage(data);
            ImageIO.write(image, "jpg", out);
        }catch(IOException e){
            System.err.println("File not found");
            System.exit(1);
        }
    }
    
    /**
     * Ler uma image no formato ppm.
     * @param file O arquivo de entrada da imagem
     * @return Os pixels da imagem em RGB, no intervalo de [0,255]
     * @throws FileNotFoundException 
     */
    public float[][] readPPM(File file) throws FileNotFoundException
    {
        Scanner scan = new Scanner(file);
        float[][] data;
        float pixelSize;
        //descarta a linha p3...
        scan.nextLine();
        
        data = new float[scan.nextInt() * 3][scan.nextInt()];
        //obtém a largura do pixel;
        pixelSize = (float)scan.nextInt();
        
        for(int j = 0; j < data[0].length; j++)
        {
            for(int i = 0; i < data.length; i++)
            {
                //ler cada componente do pixel e escala para o intervalo [0,255]
                data[i][j] = (float)(scan.nextInt() / pixelSize) * 255f;
            }
        }
        
        return data;
    }
    
    /**
     * Recebe uma matriz contendo pixels de uma imagem
     * e gera uma BufferedImage.
     * @param data A matriz de pixels
     * @return A BufferedImage resultante.
     */
    public BufferedImage makeImage(float data[][])
    {
        BufferedImage img = new BufferedImage(data.length / 3, data[0].length, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = img.getRaster();
        float[] color = new float[3];
        
        
        for(int j = 0; j < data[0].length; j++)
        {
            for(int i = 0; i < data.length / 3; i++)
            {
                color[0] = data[i * 3][j];
                color[1] = data[i * 3 + 1][j];
                color[2] = data[i * 3 + 2][j];
                
                raster.setPixel(i, j, color);
            }
        }
        
        return img;
    }
}

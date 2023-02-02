package engine.graphics.model;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex;
import engine.maths.Vector2f;
import engine.maths.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

    public static Mesh loadObjModel(String filename){
        FileReader fr = null;
        try {
            fr = new FileReader(new File("src/main/resources/model/test/" + filename + ".obj"));
        } catch (FileNotFoundException e){
            System.err.println("Couldn't load file!");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();
        float[] verticesArray = null;
        Vertex[] vertexArray = null;
        int[] indicesArray = null;

        try {
            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if(line.startsWith("vt ")){
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if(line.startsWith("vn ")){
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if(line.startsWith("f ")){
                    vertexArray = new Vertex[vertices.size()];
                    break;
                }

            }

            while(line!= null){
                if(!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, vertices, indices, textures, normals, vertexArray);
                processVertex(vertex2, vertices, indices, textures, normals, vertexArray);
                processVertex(vertex3, vertices, indices, textures, normals, vertexArray);
                line = reader.readLine();
            }

            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for(Vector3f vertex: vertices){
            verticesArray[vertexPointer++] = vertex.getX();
            verticesArray[vertexPointer++] = vertex.getY();
            verticesArray[vertexPointer++] = vertex.getZ();
        }

        for(int i=0; i<indices.size(); i++){
            indicesArray[i] = indices.get(i);
        }

        try{
            return new Mesh(vertexArray, indicesArray, new Material("src/main/resources/model/test/"+ filename + ".png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static void processVertex(String[] vertexData, List<Vector3f> verticies, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, Vertex[] vertexArray){
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        // add texture coords to vertex
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
        // add normal to vertex
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);

        vertexArray[currentVertexPointer] = new Vertex(verticies.get(currentVertexPointer), currentTex, currentNorm );
    }
}

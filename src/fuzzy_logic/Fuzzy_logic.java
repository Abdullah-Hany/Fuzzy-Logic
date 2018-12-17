/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy_logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Beido
 */
public class Fuzzy_logic {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Assignment3-Sample.txt"));

        int input_number = scanner.nextInt();
        ArrayList<Var_info> inputs = new ArrayList();

        //reading from file
        //getting input variables information
        for (int i = 0; i < input_number; i++) {
            Var_info new_input = new Var_info();
            new_input.setName(scanner.next());
            new_input.setCrisp_value(Double.parseDouble(scanner.next()));
            int membership_number = scanner.nextInt();
            new_input.setMembership_number(membership_number);

            ArrayList<membership> memberships = new ArrayList();

            for (int j = 0; j < membership_number; j++) {
                membership membership_object = new membership();
                membership_object.setName(scanner.next());
                String type = scanner.next();
                membership_object.setType(type);

                ArrayList<Double> values = new ArrayList();
                int counter;
                if (type.equals("trapezoidal")) {
                    counter = 4;
                } else if (type.equals("triangle")) {
                    counter = 3;
                } else {
                    counter = 0;
                    System.out.println("invalid membership type !! ");
                    System.exit(0);
                }

                for (int z = 0; z < counter; z++) {
                    values.add(scanner.nextDouble());
                }
                membership_object.setValue(values);
                memberships.add(membership_object);
                new_input.setMemberships(memberships);
            }

            inputs.add(new_input);
        }
        Var_info output = new Var_info();
        // saving the output variable info 
        output.setName(scanner.next());
        int membership_number = scanner.nextInt();
        output.setMembership_number(membership_number);
        ArrayList<membership> memberships = new ArrayList();

        for (int j = 0; j < membership_number; j++) {
            membership membership_object = new membership();
            membership_object.setName(scanner.next());
            String type = scanner.next();
            membership_object.setType(type);

            ArrayList<Double> values = new ArrayList();
            int counter;
            if (type.equals("trapezoidal")) {
                counter = 4;
            } else if (type.equals("triangle")) {
                counter = 3;
            } else {
                counter = 0;
                System.out.println("invalid membership type !! ");
                System.exit(0);
            }

            for (int z = 0; z < counter; z++) {
                values.add(scanner.nextDouble());
            }
            membership_object.setValue(values);
            memberships.add(membership_object);
            output.setMemberships(memberships);
        }

        calculate_membership(inputs);  //fuzzification
        
        System.out.println("fuzzification \n\n");
        printing(inputs);

        ArrayList<Rules> rules = rules (inputs,scanner);       //inference rules
        System.out.println("\n\n inference rules \n\n");
        printing_rules(rules);
        points(output);
        set_centeroid(output);
        System.out.println("\n\n polygon points and thier centeroids \n\n");
        print_points(output);
        
        System.out.print("\n\n answer of deffuzification is : ");
        System.out.println (defuzzification(output, rules));
        


    }

    public static double defuzzification (Var_info output , ArrayList<Rules> rules)
    {
        double answer = 0 ;
        double numerator = 0 ,denominator = 0;
        for (int i = 0 ; i< rules.size();i++)
        {
            double rule_value =rules.get(i).getValue() ;
            String rule_name = rules.get(i).getName();
            denominator+=rule_value;
            
            numerator+= rule_value*get_centeroid(output, rule_name);
            
        }
        answer = numerator / denominator;
        return answer;
    }
    
    public static double get_centeroid( Var_info output ,String name )
    {
        double answer = 0;
        
        for (int i = 0 ; i< output.getMembership_number();i++)
        {
            if (output.getMemberships().get(i).getName().equals(name))
            {
                
                answer = output.getMemberships().get(i).getCentroid();
            }
        }
        
        return answer;
    }
    public static void points (Var_info output)
    {
        
        for (int i = 0 ; i<output.getMembership_number();i++)
        {   
            ArrayList<Point> points = new ArrayList(); 
            if (output.getMemberships().get(i).getType().equals("trapezoidal"))
            {
                for (int j = 0 ; j< 4 ; j++)
                {
                    Point new_point = new Point();
                    new_point.setX(output.getMemberships().get(i).getValue().get(j));
                    if (j == 0 || j ==3)
                    {
                        new_point.setY(0);
                     
                    }
                    else 
                    {
                        new_point.setY(1);
                    }
                  points.add(new_point);
                }
                
            }
            else 
            {
                for (int j = 0 ; j< 3 ; j++)
                {
                    Point new_point = new Point();
                    new_point.setX(output.getMemberships().get(i).getValue().get(j));
                    if (j == 0 || j ==2)
                    {
                        new_point.setY(0);
                     
                    }
                    else 
                    {
                        new_point.setY(1);
                    }
                  points.add(new_point);
                }
            }
            
            output.getMemberships().get(i).setPoints(points);
        }
    }
    public static void print_points (Var_info output )
    {
        for (int i = 0 ; i< output.getMemberships().size();i++)
        {
            System.out.print(output.getMemberships().get(i).getName()+ "  ");
            System.out.println(output.getMemberships().get(i).getType()+ "\n");
            for (int j = 0 ; j< output.getMemberships().get(i).getPoints().size();j++)
            {
                System.out.print ("X: " + output.getMemberships().get(i).getPoints().get(j).getX());
               System.out.print(" ");
                System.out.print ("Y: " + output.getMemberships().get(i).getPoints().get(j).getY());
            System.out.println();
            }
            System.out.println("centroid : " + output.getMemberships().get(i).getCentroid());
            
        }
    }
    public static ArrayList<Rules> rules(ArrayList<Var_info> input, Scanner scanner) {
     int number_of_rules =scanner.nextInt(); 
     
     ArrayList<Rules> rules = new ArrayList();
     for (int i = 0 ; i< number_of_rules;i++)
     {
         Rules new_rule = new Rules();
        
        int number_of_premises =scanner.nextInt();
        ArrayList<Double> premise_value = new ArrayList();
     ArrayList<String> operations = new ArrayList();
     for (int j = 0 ; j< number_of_premises ;j++)
     {
        String p_name = scanner.next();
        scanner.next();
        String p_membership_name = scanner.next();
        String operator = null ;
        double crisp_value = crisp_value(input, p_name, p_membership_name+"");
        
        premise_value.add(crisp_value);
        
        if (j != number_of_premises-1)
        {   operator = scanner.next();
            operations.add(operator);
        }
        
     }
     operation(premise_value,operations);
     new_rule.setValue(premise_value.get(0));
     scanner.next();
     scanner.next();
        new_rule.setName(scanner.next());
     rules.add(new_rule);
     }
        
        return rules;
    }
    
    public static void printing_rules (ArrayList<Rules> rules)
    {
        for (int i = 0 ; i< rules.size() ;i++)
        {
            System.out.print("Rule number : " + i + " = " + rules.get(i).getValue() + " "+ rules.get(i).getName());
            System.out.println();
        }
    }
    
    public static void set_centeroid (Var_info output )
    {
        double area = 0;
        double cx = 0.0;
        for ( int i = 0 ; i < output.getMembership_number();i++)
        {
            for ( int j = 0 ; j< output.getMemberships().get(i).getPoints().size()-1;j++)
            {
                Point pt = output.getMemberships().get(i).getPoints().get(j);
                Point pt2 = output.getMemberships().get(i).getPoints().get(j+1);
                area += pt.getX()*pt2.getY()-pt2.getX()*pt.getY();
                cx +=(pt.getX()+pt2.getX()) * (pt.getX()*pt2.getY()-pt2.getX()*pt.getY());
            }
        area *= (1.0 / 2.0);        
        output.getMemberships().get(i).setCentroid(cx * (1.0 / (6.0 * area)));   
        }
        
    }
    

    
    
    public static ArrayList<Double> operation (ArrayList<Double> premise_value ,ArrayList<String> operations)
    {
        for (int i = 0 ; i< operations.size();i++)
        {
            if (premise_value.size()!= 1)
            {
                double p1 = premise_value.get(0);
                double p2 = premise_value.get(1);
                String operator = operations.get(i);
                if (operator.equals("OR"))
                {
                    
                    double max = Math.max(p1, p2);
                    premise_value.set(0, max);
                    premise_value.remove(1);
                
                }
                else if (operator.equals("AND"))
                {
                    double min = Math.min(p1, p2);
                    premise_value.set(0, min);
                    premise_value.remove(1);
                }                        
                
            }
        }
        
        return premise_value;
    }

    
    //return the membership crisp value number "the answer of the fuzzification part "
    public static double crisp_value(ArrayList<Var_info> input, String name, String membership_name) {
        double crisp_value = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).getName().equals(name)) {
                for (int j = 0; j < input.get(i).getMembership_number(); j++) {
                    if (input.get(i).getMemberships().get(j).getName().equals(membership_name)) {
                        crisp_value = input.get(i).getMemberships().get(j).getCrisp_membership();
                    }
                }
            }
        }
        return crisp_value;
    }

    public static void printing(ArrayList<Var_info> input) {
        for (int i = 0; i < input.size(); i++) {
            System.out.println("name : " + input.get(i).getName());
            System.out.println("crisp value : " + input.get(i).getCrisp_value());
            System.out.println("memberships : " + input.get(i).getMembership_number());

            for (int j = 0; j < input.get(i).getMembership_number(); j++) {
                System.out.println("name   : " + input.get(i).getMemberships().get(j).getName());
                System.out.println("type   : " + input.get(i).getMemberships().get(j).getType());
                System.out.println("values : " + input.get(i).getMemberships().get(j).getValue());
                System.out.println("crisp  : " + input.get(i).getMemberships().get(j).getCrisp_membership());

                System.out.println("\n\n");
            }

        }
    }

    public static void printing(Var_info input) {

        System.out.println("name : " + input.getName());
        System.out.println("crisp value : " + input.getCrisp_value());
        System.out.println("memberships : " + input.getMembership_number());

        for (int j = 0; j < input.getMembership_number(); j++) {
            System.out.println("name   : " + input.getMemberships().get(j).getName());
            System.out.println("type   : " + input.getMemberships().get(j).getType());
            System.out.println("values : " + input.getMemberships().get(j).getValue());
            System.out.println("crisp  : " + input.getMemberships().get(j).getCrisp_membership());

            System.out.println("\n\n");
        }

    }

    public static ArrayList<Var_info> calculate_membership(ArrayList<Var_info> input) {
        for (int i = 0; i < input.size(); i++) {
            double crisp_value = input.get(i).getCrisp_value();
            for (int j = 0; j < input.get(i).getMembership_number(); j++) {
                ArrayList<Double> values = input.get(i).getMemberships().get(j).getValue();
                if (input.get(i).getMemberships().get(j).getType().equals("trapezoidal")) {
                    input.get(i).getMemberships().get(j).setCrisp_membership(trapezoid(crisp_value, values));
                } else {
                    input.get(i).getMemberships().get(j).setCrisp_membership(triangle(crisp_value, values));
                }
            }
        }
        return input;
    }

    public static double trapezoid(double input, ArrayList<Double> values) {
        double start, upperLeft, upperRight, end, risingSlope, fallingSlope;
        start = values.get(0);
        upperLeft = values.get(1);
        upperRight = values.get(2);
        end = values.get(3);

        risingSlope = 1.0 / (upperLeft - start);
        fallingSlope = 1.0 / (end - upperRight);

        if (input <= start) {
            //out of bounds
            return 0.0;
        } else if (input < upperLeft) {
            //rising slope
            return (input - start) * risingSlope;
        } else if (input <= upperRight) {
            //plateau
            return 1.0;
        } else if (input < end) {
            //falling slope
            return (end - input) * fallingSlope;
        } else {
            //out of bounds
            return 0.0;
        }

    }

    public static double triangle(double input, ArrayList<Double> values) {
        double start, center, end, risingSlope, fallingSlope;
        start = values.get(0);
        center = values.get(1);
        end = values.get(2);

        risingSlope = 1.0 / (center - start);
        fallingSlope = 1.0 / (end - center);

        if (input <= start) {
            //out of bounds
            return 0.0;
        } else if (input < center) {
            //rising slope
            return (input - start) * risingSlope;
        } else if (input == center) {
            return 1.0;
        } else if (input <= end) {
            //falling slope
            return (end - input) * fallingSlope;
        } else {
            //out of bounds
            return 0.0;
        }
    }

}

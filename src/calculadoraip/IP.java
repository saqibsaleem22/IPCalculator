/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraip;

/**
 *This class creates an IP Address and its network, broadcast,
 * and first and last IP Addresses belonging to same network
 * @author saqib
 */
public class IP {
    
    /*
    Class properties
    address : Its an int which contains the IP address itself in an Integer from.
    mask: Contains created mask as facilitated by user in integer from.
    network: Contains network in integer from.
    broadcast: Contains broadcast in integer form.
    value: Contains the power of 2 to be used for calculating hosts.
    */
    private int address;
    private int mask;
    private int network;
    private int broadcast;
    private int value;
    
    /**
     * Only constructor of this class.It gives 
     * values to all properties by using different 
     * class methods
     * @param octates 
     */
    public IP(String... octates) {

        int[] oct = checkIP(octates);
        createAddress_Mask(oct);
        setNetwork();
        setBroadcast();
        //stores value which lately will be used as power of 2 to find total hosts
        this.value=32-Integer.parseInt(octates[4]); 

    }
    
    /**
     * Checks if the value in every octet is
     * correct and changes it into int
     * @param octates
     * @return int [] with octet in every  position. Last position contains mask value. 
     */
    private int[] checkIP(String... octates) {

        int value;
        int[] result = new int[5];
        for (int i = 0; i < result.length; i++) {
            value = Integer.parseInt(octates[i]);

            if (value < 0 || value > 255) {
                throw new NumberFormatException();  //throws exception if value is incorrect
            } else {
                result[i] = value; //if correct saves it in a position of array
            }
        }

        return result;

    }
    
    /**
     * Sets the address and mask properties by 
     * using bitwise operations.
     * @param octates An array with ip octet.Last position contains mask value. 
     */
    private void createAddress_Mask(int[] octates) {
        
        //moving different octates accordingly to convert it into an int
        this.address = (octates[0] << 24) | (octates[1] << 16) | (octates[2] << 8) | (octates[3]);
        this.mask = calculateMask(octates[4]);
    }
    
    /**
     * Calculates mask by using mask value
     * @param m The value of mask
     * @return The mask in int from
     */
    private int calculateMask(int m) {
        /*The power of two is mask value and by subtracting 1 we create
        an int which has m number of ones 
        */
        int mask = (int) (Math.pow(2, m) - 1);
        //moving the ones to the start of number accordingly
        return mask << (32 - m);
    }
    
    /**
     * Sets the network by doing AND operation 
     * between address and mask properties.
     */
    private void setNetwork() {
        this.network = this.address & this.mask;
    }
    
    /**
     * Sets broadcast property and OR operation 
     * between network and negation of mask.
     */
    private void setBroadcast() {
        this.broadcast = this.network | (~this.mask);
    }
    
    /**
     * Checks if the IP introduced belongs to same network
     * @param second
     * @return true or false
     */
    public boolean sameNetwork(IP second) {
        boolean same = false;
        //Checks if both ips have same network
        if (second.network == this.network) {
            same = true;
        }
        return same;

    }
    
    /**
     * Covert int form of addresses to 4 octet.
     * @param ip the ip address in int form
     * @return A String with properly arranged octet.
     */
    private String toOctates(int ip) {
        
        return String.format("%d.%d.%d.%d", (ip >> 24 & 0xff), (ip >> 16 & 0xff), (ip >> 8 & 0xff), (ip & 0xff));
    }
    
    /**
     * This method returns a String with all
     * properties in octet form.It uses toOcates method 
     * to give it format of IP in 4 octet form
     * @return String
     */
    @Override
    public String toString() {
        String as = "IP address: " + toOctates(this.address);
        String nk = "\nNetwork ID: " + toOctates(this.network)+ " /"+(32-this.value); //netword id and mask.
        String bt = "\nBroadcast ID: " + toOctates(this.broadcast);
        String ft = "\nFirst address: " + toOctates(this.network + 1);
        String ls = "\nLast address: " + toOctates(this.broadcast - 1);
        String ta = "\nTotal hosts: " + (int)(Math.pow(2, this.value)-2);

        return as + nk + bt + ft + ls + ta;

    }

}

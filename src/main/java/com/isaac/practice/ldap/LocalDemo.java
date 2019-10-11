package com.isaac.practice.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LocalDemo {
    public static List<String> getAllPersons(){
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
        env.put(Context.REFERRAL, "follow");

        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
        } catch (NamingException e){
            e.printStackTrace();
            return null;
        }

        List<String> li = new LinkedList<>();
        NamingEnumeration results;

        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            results = ctx.search("dc=example,dc=com", "objectClass=person", controls);
            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();
                Attribute attr = attributes.get("userPassword");
                String cn = Optional.ofNullable(attr).map(att -> {
                    try {
                        return att.get().toString();
                    } catch (NamingException e
                    ) {
                        return "";
                    }
                }).orElse("");
                li.add(cn);
            }
        }catch (Exception ignored){

        }

        return li;
    }

    public static void main(String[] args) {
        System.out.println(getAllPersons());
    }
}

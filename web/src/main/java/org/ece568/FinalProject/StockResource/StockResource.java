package org.ece568.FinalProject.StockResource;

import java.util.List;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ece568.FinalProject.model.Comment;
import org.ece568.FinalProject.model.RealTimeStock;
import org.ece568.FinalProject.service.StockService;
import org.glassfish.jersey.server.JSONP;
import org.json.JSONArray;
import org.json.JSONObject;

import javassist.compiler.ast.Symbol;

@Path("stockresource")
public class StockResource {
	
	StockService stockService = new StockService();

    @GET
    @JSONP(queryParam = JSONP.DEFAULT_QUERY)
    @Produces("application/x-javascript")
	public List<RealTimeStock> getStockResource(@QueryParam(JSONP.DEFAULT_QUERY) String callback) {
    		return stockService.getAllStock();
    }
    
    
    @GET
    @Path("/{symbol}")
    @JSONP(queryParam = JSONP.DEFAULT_QUERY)
    @Produces("application/x-javascript")
    public Response getValue(@QueryParam(JSONP.DEFAULT_QUERY) String callback,
    		@PathParam("symbol") String symbol, @QueryParam("type") String type) {
    		if(type.equals("high")) {
    			System.out.println("开始查询" + symbol );
    			return stockService.getMax(symbol);
    		} else if (type.equals("avg")) {
    			return stockService.getAvg(symbol);
    		} else if (type.equals("low")) {
    			return stockService.getMin(symbol);
    		} else if(type.equals("day")) {
    			return stockService.getDay(symbol);
    		} else if(type.equals("month")) {
    			return stockService.getMonth(symbol);
    		} else if (type.equals("year")) {
    			return stockService.getYear(symbol);
    		} else if(type.equals("comment")) {
    			return stockService.getComment(symbol);
    		} else if(type.equals("loweravg")) {
    			return stockService.getlowerAvgSymbol(symbol);
    		}
    		return null;
    }
    
    @POST
    @Path("/comment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(
    		@FormParam("username") String username,
    		@FormParam("symbol") String symbol,
    		@FormParam("timestamp") String timestamp,
    		@FormParam("comment") String comment
    		) {
    		return stockService.addComment( symbol,  comment,  timestamp,  username);
    }
    
}
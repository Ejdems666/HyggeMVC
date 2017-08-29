# Routing

To use the routing engine create a Filter class with this mapping in web.xml:

    <filter>
        <filter-name>MyRequestDispatcher</filter-name>
        <filter-class>my.app.RequestDispatcher</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>MyRequestDispatcher</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

and extend the HyggeMVC RequestDispatcher and implement routeToController() method

In this method you can create your routes and call a corresponding controller. viz an example:
   
     protected void routeToController(HttpServletRequest request, HttpServletResponse response, String url) {
              Router router =
              new Router(
                  new Route("(?<method>[a-z\\-]+)?","Default","index"),
                  new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)?","Default","index")
              );
             EndpointReflection endpointReflection = router.getEndpointReflection("your.controllers.package",url);
             AppContainer app = new AppContainer(request,response);
             app.run(endpointReflection);
     }

As you can see routes are using regular expressions with named groups.

The example routes will match and work with following endpoint examples:

    1. /item ............. ItemController.index()
    2. /item/long-name ... ItemController.longName()
    3. /item/edit ........ ItemControler.edit()
    4. /item ............. DefaultController.item()
    5. / ................. DefaultController.index()
    
When creating routes it is important to note that routes are evaluated in sequence. Therefore if the first route that matches the url and the requested controller and method exist the programme continues into the controller method and no other route is evaluated.

So if methods in example 1 and 4 both exist, DefaultController.item() will be called, because it comes from the match of the first route, even thought the second route can match the same url.

Also notice that the url names are translated to camelCase, viz example 2

If the value in url is missing, default values specified after first argument in Route constructor, are used instead, viz example 1, 4 and 5.

Note that the first symbol "/" is already being accounted for in the routing engine, so don't include it in the rout pattern.

Also the trailing slashes (...(?\<method\>**/**[a-z\\-]+)?) are handled by the engine automatically, so there is no need to handle them in the pattern. 

## Method arguments

It is possible to pass argument to endpoint methods straight from the url, by specifying them in the route pattern.

If we modify the routes as follows:

    ...
    new Route("(?<method>[a-z\\-]+)?(?<int>/\\d+)?","Default","index"),
    new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)?(?<int>/\\d+)?","Default","index")
    ...

These routes will match and work with following endpoint examples:

    1. /item/1 ............. ItemController.index(1)
    3. /item/edit/1 ........ ItemControler.edit(1)
    4. /item/1 ............. DefaultController.item(1)
    
All the endpoints showed in previous example will still work.

### String method parameters

It is also possible to have string parameters. However this might conflict the dynamic routes, so it's important to think about the order in which you pass routes to the router.

Having a this route on the first place in the router constructor:

    new Route("item/(?<method>[a-z\\-]+)/(?<string0>\\w+)","Item","index")

would work like this:
    
    1. /item/edit/some-name -> ItemController.add("some-name")
    
and all previous endpoint examples are still valid.

## Custom URLs

Thanks to the Default Controller and method values like "Default","index", custom url endpoints are possible.

For instance if you make a this route:

    new Route("custom-url","Custom","methodOfAnyName")

and put it in front of all the general routes (so it would be able to match before the general ones) the following endpoint would work:

    /custom-url -> CustomController.methodOfAnyName()

## Modules

If you want to structure your controllers to sub packages, you can by adding a fourth argument to the route constructor:

    new Route("custom-url","Custom","methodOfAnyName","custom.subpackage")

this would work as follows:

    /custom-url -> custom.subpackage.CustomController.methodOfAnyName()

You can still use dynamic route for the modules:

    new Route("(?<module>[a-z]+)(?<controller>/[a-z\\-]+)(?<method>/[a-z\\-]+)?", "Default", "index")

Would work like this:

    /package/controller-name/method-name -> package.ControllerNameController.methodName()

# Returning data from Controller

Controller methods can either be returning void, in which case nothing will be rendered or send back to the browser.

Or they can return a Result type object.

## Rendering template

Currently the only inbuild Result type class is JspResult which is used to render a template.

Example:

    public Result index() {
        return new JspResult("templateName");
    }
 
This means that the engine will forward request to index.jsp which is a default layout jsp file and inside index.jsp you have access to the passed template name to include the unique templateName.jsp file

You can also specify the layout name if you want to use different layout than index.jsp

You can also pass the instance of the constructor which will set the template name automatically based on the name of the controller method and optionally module/sub-package:

    public class DefaultController extends Controller {
        public Result index() {
            return new JspResult(this);
        }
    }

This will set templateName as default/index.jsp.

If the Controller is in subpackage "example" the template name will be: example/default/index.jsp

It is recommended to use this approach, as it helps with keeping track of JSPs.

## Returning other types of data

Json and XML result types are coming soon.


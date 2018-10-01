using Microsoft.AspNetCore.Mvc;
using System;

namespace Pollite.Controllers
{
    // [Route("/api/[controller]")]
    public class NotFoundController : Controller 
    {
        // [HttpGet("[action]")]
        public JsonResult Index()
        {
            Console.WriteLine("not found...");
            return Json("not found");
        }
    }
}
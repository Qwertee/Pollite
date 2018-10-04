using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace PolliteBackend.Controllers
{
    [ApiController]
    public class NotFoundController : Controller
    {
        // GET api/values
        [HttpGet]
        public JsonResult Get()
        {
            return Json("not found");
        }
    }
}

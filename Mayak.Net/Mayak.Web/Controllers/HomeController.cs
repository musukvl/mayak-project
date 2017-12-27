using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Mayak.Model;

namespace Mayak.Web.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            var locations = new List<Location>();
            using (var ctx = new MayakDbContext())
            {
                locations = ctx.Locations.OrderByDescending(x => x.Date).ToList();
            }
            ViewBag.Locations = locations;

            return View();
        }
    }
}
